import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { defaultDashboardRouteForRole, isAllowedDashboardPath } from "@/lib/auth";
import { CLIENT_AUTH_COOKIE } from "@/lib/api";

function decodeRoleFromJwt(token: string | undefined): string | null {
  if (!token) return null;
  try {
    const payload = token.split(".")[1];
    if (!payload) return null;
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    const padded = base64.padEnd(Math.ceil(base64.length / 4) * 4, "=");
    const atobFn = (globalThis as { atob?: (s: string) => string }).atob;
    if (!atobFn) return null;
    const parsed = JSON.parse(atobFn(padded)) as { role?: string; exp?: number };
    if (typeof parsed.exp === "number" && parsed.exp * 1000 < Date.now()) return null;
    return parsed.role ?? null;
  } catch {
    return null;
  }
}

function accessTokenFromRequest(req: NextRequest): string | undefined {
  const httpOnly = req.cookies.get("access_token")?.value;
  if (httpOnly) return httpOnly;
  const raw = req.cookies.get(CLIENT_AUTH_COOKIE)?.value;
  if (!raw) return undefined;
  try {
    return decodeURIComponent(raw);
  } catch {
    return raw;
  }
}

export function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;
  const token = accessTokenFromRequest(req);
  const role = decodeRoleFromJwt(token);
  const isAuthed = !!role;

  if (pathname === "/") {
    const url = req.nextUrl.clone();
    url.pathname = isAuthed ? defaultDashboardRouteForRole(role) : "/login";
    return NextResponse.redirect(url);
  }

  if (pathname.startsWith("/login")) {
    if (isAuthed) {
      const url = req.nextUrl.clone();
      url.pathname = defaultDashboardRouteForRole(role);
      return NextResponse.redirect(url);
    }
    return NextResponse.next();
  }

  if (pathname.startsWith("/dashboard")) {
    if (!isAuthed) {
      const url = req.nextUrl.clone();
      url.pathname = "/login";
      return NextResponse.redirect(url);
    }
    if (!isAllowedDashboardPath(role, pathname)) {
      const url = req.nextUrl.clone();
      url.pathname = defaultDashboardRouteForRole(role);
      return NextResponse.redirect(url);
    }
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/", "/login", "/dashboard/:path*"],
};
