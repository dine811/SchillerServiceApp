"use client";

import { useCallback, useEffect, useState } from "react";
import type { NavSection } from "@/config/navigation";
import { dashboardNavSections } from "@/config/navigation";

const STORAGE_KEY = "sis-nav-sections-expanded";

const defaultExpanded: Record<NavSection["id"], boolean> = {
  master: true,
  services: true,
  activity: true,
};

function routeMatchesPath(pathname: string | null, href: string): boolean {
  if (!pathname) return false;
  if (href === "/dashboard") {
    return pathname === "/dashboard" || pathname === "/dashboard/";
  }
  return pathname === href || pathname.startsWith(href + "/");
}

function loadStored(): Record<NavSection["id"], boolean> | null {
  if (typeof window === "undefined") return null;
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return null;
    const parsed = JSON.parse(raw) as Partial<Record<NavSection["id"], boolean>>;
    return {
      master: parsed.master ?? defaultExpanded.master,
      services: parsed.services ?? defaultExpanded.services,
      activity: parsed.activity ?? defaultExpanded.activity,
    };
  } catch {
    return null;
  }
}

function persist(state: Record<NavSection["id"], boolean>) {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
  } catch {
    /* ignore */
  }
}

/**
 * Expand/collapse state for sidebar (and dashboard quick nav).
 * Persists to localStorage; auto-expands the section that contains the active route.
 */
export function useNavSectionsExpanded(pathname: string | null) {
  const [expanded, setExpanded] = useState<Record<NavSection["id"], boolean>>(defaultExpanded);
  const [hydrated, setHydrated] = useState(false);

  useEffect(() => {
    const stored = loadStored();
    if (stored) setExpanded(stored);
    setHydrated(true);
  }, []);

  /** When the route changes, expand every section that contains the active page (legacy submenu behaviour). */
  useEffect(() => {
    if (!pathname || !hydrated) return;
    setExpanded((prev) => {
      const next = { ...prev };
      let changed = false;
      for (const section of dashboardNavSections) {
        const hasActive = section.items.some((item) => routeMatchesPath(pathname, item.href));
        if (hasActive && !next[section.id]) {
          next[section.id] = true;
          changed = true;
        }
      }
      if (changed) persist(next);
      return changed ? next : prev;
    });
  }, [pathname, hydrated]);

  const toggle = useCallback((id: NavSection["id"]) => {
    setExpanded((prev) => {
      const next = { ...prev, [id]: !prev[id] };
      persist(next);
      return next;
    });
  }, []);

  const setSection = useCallback((id: NavSection["id"], open: boolean) => {
    setExpanded((prev) => {
      if (prev[id] === open) return prev;
      const next = { ...prev, [id]: open };
      persist(next);
      return next;
    });
  }, []);

  return { expanded, toggle, setSection };
}
