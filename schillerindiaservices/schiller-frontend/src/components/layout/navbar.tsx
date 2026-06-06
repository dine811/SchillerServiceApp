"use client";

import { useEffect, useState } from "react";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { Bell, LogOut, Search, Settings, User } from "lucide-react";
import { apiFetch, clearClientAuthToken, getApiBaseUrl } from "@/lib/api";

type AuthProfile = {
  name: string;
  role: string;
};

export const Navbar = () => {
  const [mounted, setMounted] = useState(false);
  const [profile, setProfile] = useState<AuthProfile | null>(null);

  useEffect(() => {
    setMounted(true);
  }, []);

  useEffect(() => {
    let cancelled = false;
    (async () => {
      try {
        const res = await apiFetch(`${getApiBaseUrl()}/auth/me`);
        if (!res.ok) return;
        const raw = (await res.text().catch(() => "")).trim();
        if (!raw) return;
        const parsed = JSON.parse(raw) as { name?: string; role?: string };
        if (!cancelled && parsed.role) {
          setProfile({
            name: parsed.name?.trim() || "User",
            role: parsed.role,
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

  const handleLogout = async () => {
    try {
      await apiFetch(`${getApiBaseUrl()}/auth/logout`, { method: "POST" });
    } catch {
      /* still clear client session */
    } finally {
      clearClientAuthToken();
      window.location.href = "/login";
    }
  };

  const displayName = profile?.name ?? "…";
  const roleLabel = (profile?.role ?? "—").replaceAll("_", " ");
  const initials = (profile?.name || "U")
    .split(/\s+/)
    .filter(Boolean)
    .slice(0, 2)
    .map((p) => p[0]?.toUpperCase() ?? "")
    .join("") || "U";

  if (!mounted) {
    return (
      <div className="sticky top-0 z-40 flex h-16 min-w-0 items-center justify-between border-b border-border/60 bg-white/70 px-6 backdrop-blur-md">
        <div className="hidden md:flex items-center gap-2 px-3 py-2 rounded-xl bg-slate-100/80 border border-slate-200/80 w-64 text-sm text-slate-400">
          <Search className="w-4 h-4 shrink-0" />
          <span>Search anything...</span>
        </div>
        <div className="flex items-center gap-3 ml-auto">
          <button className="relative w-9 h-9 rounded-xl flex items-center justify-center bg-slate-100">
            <Bell className="w-4 h-4 text-slate-600" />
          </button>
          <button className="w-9 h-9 rounded-xl flex items-center justify-center bg-slate-100">
            <Settings className="w-4 h-4 text-slate-600" />
          </button>
          <div className="flex items-center gap-2.5 pl-2 pr-3 py-1.5 rounded-xl">
            <Avatar className="h-7 w-7">
              <AvatarFallback className="text-white text-xs font-bold" style={{ background: "linear-gradient(135deg,#6366f1,#8b5cf6)" }}>…</AvatarFallback>
            </Avatar>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="sticky top-0 z-40 flex h-16 min-w-0 items-center justify-between border-b border-border/60 bg-white/70 px-6 backdrop-blur-md">
      {/* Search bar */}
      <div className="hidden md:flex items-center gap-2 px-3 py-2 rounded-xl bg-slate-100/80 border border-slate-200/80 w-64 text-sm text-slate-400">
        <Search className="w-4 h-4 shrink-0" />
        <span>Search anything...</span>
      </div>

      <div className="flex items-center gap-3 ml-auto">
        {/* Notification bell */}
        <button className="relative w-9 h-9 rounded-xl flex items-center justify-center bg-slate-100 hover:bg-slate-200 transition-all">
          <Bell className="w-4 h-4 text-slate-600" />
          <span className="absolute top-1.5 right-1.5 w-2 h-2 bg-violet-500 rounded-full border-2 border-white" />
        </button>

        {/* Settings */}
        <button className="w-9 h-9 rounded-xl flex items-center justify-center bg-slate-100 hover:bg-slate-200 transition-all">
          <Settings className="w-4 h-4 text-slate-600" />
        </button>

        {/* User menu */}
        <DropdownMenu>
          <DropdownMenuTrigger className="focus:outline-none">
            <div className="flex items-center gap-2.5 pl-2 pr-3 py-1.5 rounded-xl hover:bg-slate-100 transition-all cursor-pointer">
              <Avatar className="h-7 w-7">
                <AvatarFallback className="text-white text-xs font-bold" style={{ background: "linear-gradient(135deg,#6366f1,#8b5cf6)" }}>{initials}</AvatarFallback>
              </Avatar>
              <div className="hidden md:block text-left min-w-0 max-w-[140px]">
                <p className="text-[12px] font-semibold text-slate-800 leading-none truncate">{displayName}</p>
                <p className="text-[11px] text-slate-500 leading-none mt-0.5 truncate">{roleLabel}</p>
              </div>
            </div>
          </DropdownMenuTrigger>
          <DropdownMenuContent
            align="end"
            sideOffset={8}
            className="w-56 rounded-xl border border-slate-200 bg-white p-1.5 shadow-lg"
          >
            <DropdownMenuLabel className="px-2 py-1.5 text-xs font-medium text-slate-500">My Account</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuItem className="cursor-pointer gap-2 rounded-lg px-2 py-2 text-sm text-slate-700">
              <User className="w-4 h-4" /> Profile
            </DropdownMenuItem>
            <DropdownMenuItem className="cursor-pointer gap-2 rounded-lg px-2 py-2 text-sm text-slate-700">
              <Settings className="w-4 h-4" /> Settings
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem
              className="cursor-pointer gap-2 rounded-lg px-2 py-2 text-sm text-red-600 focus:bg-red-50 focus:text-red-600"
              onClick={handleLogout}
            >
              <LogOut className="w-4 h-4" /> Logout
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </div>
  );
};
