export type AppRole =
  | "ADMIN"
  | "ENGINEER"
  | "FQC"
  | "VICE_CHANCELLOR"
  | "SERVICE_COORDINATOR"
  | "REPAIR_TEAM"
  | "PRODUCT_SUPPORT";

export function defaultDashboardRouteForRole(role: string | null | undefined): string {
  const r = (role ?? "").toUpperCase();
  switch (r) {
    case "ADMIN":
      return "/dashboard";
    case "ENGINEER":
      return "/dashboard/engineer";
    case "FQC":
      return "/dashboard/fqc";
    case "VICE_CHANCELLOR":
      return "/dashboard/reports";
    case "SERVICE_COORDINATOR":
      return "/dashboard/service-coordinator";
    case "REPAIR_TEAM":
      return "/dashboard/under-repair";
    case "PRODUCT_SUPPORT":
      return "/dashboard/prf-ob-admin";
    default:
      return "/dashboard";
  }
}

const BASE_SHARED = ["/dashboard", "/dashboard/settings"];
const ADMIN_ONLY_PREFIXES = ["/dashboard/employees", "/dashboard/divisions", "/dashboard/dealers"];

export function isAllowedDashboardPath(role: string | null | undefined, pathname: string): boolean {
  const r = (role ?? "").toUpperCase();
  if (r === "ADMIN") return pathname.startsWith("/dashboard");

  const allowed: string[] = [...BASE_SHARED];
  if (r === "ENGINEER") {
    allowed.push(
      "/dashboard/engineer",
      "/dashboard/services",
      "/dashboard/under-repair",
      "/dashboard/pending-frn",
      "/dashboard/ob-pending",
      "/dashboard/sc-closed",
      "/dashboard/new-closed",
      "/dashboard/scrap-list",
      "/dashboard/call-list",
      "/dashboard/closed-calls",
      "/dashboard/pending-activity",
      "/dashboard/closed-activity",
      "/dashboard/prf-ob-admin",
      "/dashboard/prf-ob-closed",
      "/dashboard/non-salable-admin",
      "/dashboard/salables",
      "/dashboard/bir-admin",
      "/dashboard/bir-closed-admin",
      "/dashboard/spares",
      "/dashboard/spares-completed"
    );
  } else if (r === "FQC") {
    allowed.push(
      "/dashboard/fqc",
      "/dashboard/pending-activity",
      "/dashboard/closed-activity",
      "/dashboard/non-salable-admin",
      "/dashboard/salables",
      "/dashboard/bir-admin",
      "/dashboard/bir-closed-admin"
    );
  } else if (r === "SERVICE_COORDINATOR") {
    allowed.push(
      "/dashboard/service-coordinator",
      "/dashboard/prf-ob-admin",
      "/dashboard/prf-ob-closed",
      "/dashboard/spares",
      "/dashboard/spares-completed"
    );
  } else if (r === "PRODUCT_SUPPORT" || r === "REPAIR_TEAM") {
    allowed.push("/dashboard/services", "/dashboard/call-list", "/dashboard/prf-ob-admin", "/dashboard/non-salable-admin", "/dashboard/bir-admin");
    if (r === "REPAIR_TEAM") allowed.push("/dashboard/under-repair");
  } else if (r === "VICE_CHANCELLOR") {
    allowed.push("/dashboard/reports", "/dashboard/prf-ob-closed", "/dashboard/bir-closed-admin", "/dashboard/salables", "/dashboard/closed-calls");
  }

  if (ADMIN_ONLY_PREFIXES.some((p) => pathname.startsWith(p))) return false;
  return allowed.some((p) => pathname === p || pathname.startsWith(`${p}/`));
}

export function decodeRoleFromJwt(token: string | undefined): string | null {
  if (!token) return null;
  try {
    const payload = token.split(".")[1];
    if (!payload) return null;
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    const padded = base64.padEnd(Math.ceil(base64.length / 4) * 4, "=");
    const parsed = JSON.parse(atob(padded)) as { role?: string; exp?: number };
    if (typeof parsed.exp === "number" && parsed.exp * 1000 < Date.now()) return null;
    return parsed.role ?? null;
  } catch {
    return null;
  }
}
