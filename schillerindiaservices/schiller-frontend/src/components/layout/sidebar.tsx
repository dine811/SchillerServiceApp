"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";
import { ChevronDown, ChevronRight } from "lucide-react";
import { navSectionsForRole } from "@/config/navigation";
import { useNavSections } from "@/contexts/nav-sections-context";
import { apiFetch, getApiBaseUrl } from "@/lib/api";

type AuthProfile = {
  name: string;
  role: string;
  username?: string;
};

export const Sidebar = () => {
  const pathname = usePathname();
  const { expanded, toggle } = useNavSections();
  const [profile, setProfile] = useState<AuthProfile | null>(null);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const res = await apiFetch(`${getApiBaseUrl()}/auth/me`);
        if (!res.ok) return;
        const raw = (await res.text().catch(() => "")).trim();
        if (!raw) return;
        const parsed = JSON.parse(raw) as { name?: string; role?: string; username?: string };
        if (!cancelled && parsed.role) {
          setProfile({
            name: parsed.name || "User",
            role: parsed.role,
            username: parsed.username,
          });
        }
      } catch {
        /* ignore */
      }
    })();
    return () => {
      cancelled = true;
    };
  }, []);

  const sections = navSectionsForRole(profile?.role);
  const initials = (profile?.name || "Admin User")
    .split(/\s+/)
    .filter(Boolean)
    .slice(0, 2)
    .map((p) => p[0]?.toUpperCase() ?? "")
    .join("") || "AD";
  const roleLabel = (profile?.role || "ADMIN").replaceAll("_", " ");

  return (
    <div
      className="flex h-full w-full min-w-0 flex-col overflow-hidden"
      style={{ background: "linear-gradient(180deg, #0f1117 0%, #13151f 100%)" }}
    >
      {/* Logo */}
      <div className="px-5 pt-7 pb-6 border-b border-white/[0.06]">
        <Link href="/dashboard" className="flex items-center gap-3 group">
          <div className="relative w-9 h-9 rounded-xl flex items-center justify-center shrink-0"
            style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}>
            <span className="text-white font-bold text-lg">S</span>
            <div className="absolute inset-0 rounded-xl opacity-0 group-hover:opacity-30 transition-opacity"
              style={{ boxShadow: "0 0 20px 4px #6366f1" }} />
          </div>
          <div>
            <p className="text-white font-bold text-[15px] leading-none">Schiller India</p>
            <p className="text-white/40 text-[11px] mt-0.5 leading-none font-medium tracking-wide">SERVICES</p>
          </div>
        </Link>
      </div>

      {/* Nav — collapsible sections (legacy nested menus) */}
      <nav className="flex-1 min-h-0 px-3 py-4 overflow-y-auto overflow-x-hidden" aria-label="Main navigation">
        {sections.map((section, sectionIdx) => {
          const isOpen = expanded[section.id];
          return (
            <div key={section.id} className={cn(sectionIdx > 0 && "mt-2")}>
              <button
                type="button"
                onClick={() => toggle(section.id)}
                aria-expanded={isOpen}
                className={cn(
                  "flex w-full items-center gap-2 rounded-lg px-2 py-2 text-left transition-colors hover:bg-white/5",
                  section.id === "services" && "text-indigo-300/90",
                  section.id === "activity" && "text-sky-300/85",
                  section.id === "master" && "text-white/40"
                )}
              >
                <ChevronDown
                  className={cn(
                    "h-4 w-4 shrink-0 text-white/50 transition-transform duration-200",
                    !isOpen && "-rotate-90"
                  )}
                  aria-hidden
                />
                <span className="min-w-0 flex-1 truncate text-[10px] font-semibold uppercase tracking-widest">
                  {section.title}
                </span>
              </button>
              <ul
                hidden={!isOpen}
                className="space-y-0.5 mt-1"
                id={`nav-section-${section.id}`}
              >
                {section.items.map((route) => {
                  const isActive =
                    route.href === "/dashboard"
                      ? pathname === "/dashboard" || pathname === "/dashboard/"
                      : pathname === route.href || pathname?.startsWith(route.href + "/");
                  return (
                    <li key={route.href}>
                      <Link
                        href={route.href}
                        className={cn(
                          "group relative flex min-w-0 items-center gap-2.5 rounded-xl px-2.5 py-2.5 transition-all duration-200",
                          isActive
                            ? "bg-white/10 text-white"
                            : "text-white/50 hover:text-white/80 hover:bg-white/5"
                        )}
                      >
                        {isActive && (
                          <div className="absolute left-0 top-1/2 -translate-y-1/2 w-0.5 h-5 rounded-r-full"
                            style={{ background: "linear-gradient(to bottom, #6366f1, #8b5cf6)" }} />
                        )}

                        <div className={cn(
                          "w-8 h-8 rounded-lg flex items-center justify-center shrink-0 transition-all duration-200",
                          isActive
                            ? `bg-gradient-to-br ${route.gradient} shadow-lg`
                            : "bg-white/5 group-hover:bg-white/10"
                        )}>
                          <route.icon className={cn("w-4 h-4", isActive ? "text-white" : "text-white/60")} />
                        </div>

                        <span className="min-w-0 flex-1 truncate text-sm font-medium">{route.label}</span>

                        {isActive && (
                          <ChevronRight className="w-3.5 h-3.5 text-white/40 shrink-0" />
                        )}
                      </Link>
                    </li>
                  );
                })}
              </ul>
            </div>
          );
        })}
      </nav>

      {/* Bottom user section — kept inside sidebar width */}
      <div className="shrink-0 min-w-0 border-t border-white/[0.06] p-3">
        <div className="flex min-w-0 items-center gap-2.5 overflow-hidden rounded-xl bg-white/[0.04] px-2.5 py-2">
          <div
            className="flex h-8 w-8 shrink-0 items-center justify-center rounded-lg text-sm font-semibold text-white"
            style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}
          >
            {initials}
          </div>
          <div className="min-w-0 flex-1 overflow-hidden">
            <p className="truncate text-[13px] font-semibold text-white">{profile?.name || "Admin User"}</p>
            <p className="truncate text-[11px] text-white/35">{roleLabel}</p>
          </div>
        </div>
      </div>
    </div>
  );
};
