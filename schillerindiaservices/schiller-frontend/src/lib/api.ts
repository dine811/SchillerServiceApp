/**
 * Base URL for Spring Boot REST APIs (paths are like `/api/...` on the server).
 *
 * - **Default (browser):** `/api` on the same host as Next.js — proxied to the backend via `next.config` rewrites
 *   so the browser does not call `localhost:8090` directly (avoids mixed-origin / CORS issues).
 * - **SSR / Node:** `http://127.0.0.1:8090/api` so server-side fetches hit the backend directly.
 * - **NEXT_PUBLIC_API_URL:** If set, use as the API base. Accepts `http://host:8090` or `http://host:8090/api`.
 */
export function getApiBaseUrl(): string {
  const env = process.env.NEXT_PUBLIC_API_URL?.trim();
  if (env) {
    const n = env.replace(/\/$/, "");
    if (n.endsWith("/api")) return n;
    return `${n}/api`;
  }
  if (typeof window !== "undefined") {
    return `${window.location.origin}/api`;
  }
  return "http://127.0.0.1:8090/api";
}

/** Readable mirror of JWT for Next.js middleware (HttpOnly `access_token` is not visible here). */
export const CLIENT_AUTH_COOKIE = "sis_at";

const SESSION_TOKEN_KEY = "sis_access_token";

export function persistClientAuthToken(token: string): void {
  if (typeof window === "undefined") return;
  sessionStorage.setItem(SESSION_TOKEN_KEY, token);
  const maxAge = 24 * 60 * 60;
  document.cookie = `${CLIENT_AUTH_COOKIE}=${encodeURIComponent(token)}; path=/; max-age=${maxAge}; samesite=lax`;
}

export function clearClientAuthToken(): void {
  if (typeof window === "undefined") return;
  sessionStorage.removeItem(SESSION_TOKEN_KEY);
  document.cookie = `${CLIENT_AUTH_COOKIE}=; path=/; max-age=0`;
}

function readTokenFromCookie(name: string): string | null {
  if (typeof document === "undefined") return null;
  const parts = `; ${document.cookie}`.split(`; ${name}=`);
  if (parts.length < 2) return null;
  const raw = parts.pop()?.split(";").shift();
  if (!raw) return null;
  try {
    return decodeURIComponent(raw);
  } catch {
    return raw;
  }
}

/**
 * JWT for API calls. Spring accepts Bearer or HttpOnly `access_token`; we mirror the same JWT in
 * `sis_at` for middleware — if sessionStorage was cleared (new tab, privacy), fall back to `sis_at`
 * so dropdowns and other apiFetch calls still send Authorization.
 */
export function getClientAuthToken(): string | null {
  if (typeof window === "undefined") return null;
  const fromSession = sessionStorage.getItem(SESSION_TOKEN_KEY);
  if (fromSession) return fromSession;
  const fromCookie = readTokenFromCookie(CLIENT_AUTH_COOKIE);
  if (fromCookie) {
    try {
      sessionStorage.setItem(SESSION_TOKEN_KEY, fromCookie);
    } catch {
      /* quota / private mode */
    }
  }
  return fromCookie;
}

/**
 * Authenticated fetch: sends cookies and `Authorization: Bearer` when a token was stored at login.
 * Use for all Spring Boot `/api/**` calls except `/api/auth/login` (no token yet).
 */
export async function apiFetch(input: string | URL, init: RequestInit = {}): Promise<Response> {
  const headers = new Headers(init.headers ?? undefined);
  const t = getClientAuthToken();
  if (t) headers.set("Authorization", `Bearer ${t}`);
  return fetch(input, {
    ...init,
    headers,
    credentials: init.credentials ?? "include",
  });
}
