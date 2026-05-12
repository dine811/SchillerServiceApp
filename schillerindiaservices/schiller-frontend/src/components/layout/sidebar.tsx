"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";
import { Bell, ChevronDown, ChevronRight } from "lucide-react";
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
    <div className="flex flex-col h-full" style={{ background: "linear-gradient(180deg, #0f1117 0%, #13151f 100%)" }}>
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
      <nav className="flex-1 px-3 py-4 overflow-y-auto" aria-label="Main navigation">
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
                <span className="text-[10px] font-semibold tracking-widest uppercase flex-1">
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
                          "group relative flex items-center gap-3 px-3 py-2.5 rounded-xl transition-all duration-200",
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

                        <span className="flex-1 text-sm font-medium">{route.label}</span>

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

      {/* Bottom user section */}
      <div className="px-3 py-4 border-t border-white/[0.06] shrink-0">
        <div className="flex items-center gap-3 px-3 py-2.5">
          <div className="w-8 h-8 rounded-lg flex items-center justify-center shrink-0 text-white font-semibold text-sm"
            style={{ background: "linear-gradient(135deg, #6366f1, #8b5cf6)" }}>
            {initials}
          </div>
          <div className="flex-1 min-w-0">
            <p className="text-white text-[13px] font-semibold truncate">{profile?.name || "Admin User"}</p>
            <p className="text-white/35 text-[11px] truncate">{roleLabel}</p>
          </div>
          <button type="button" className="w-7 h-7 rounded-lg flex items-center justify-center text-white/40 hover:text-white hover:bg-white/10 transition-all shrink-0">
            <Bell className="w-3.5 h-3.5" />
          </button>
        </div>
      </div>
    </div>
  );
};
