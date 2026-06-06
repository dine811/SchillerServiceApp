/** Normalized JWT / API role string (e.g. SERVICE_COORDINATOR). */
export function normalizeAppRole(role: string | null | undefined): string {
  return (role ?? "").trim().toUpperCase().replace(/\s+/g, "_");
}

export function isServiceCoordinator(role: string | null | undefined): boolean {
  return normalizeAppRole(role) === "SERVICE_COORDINATOR";
}

/** Legacy PRFOB_AdminList.jsp: coordinator sees Update only, not Delete. */
export function showPrfobDeleteForRole(role: string | null | undefined): boolean {
  return !isServiceCoordinator(role);
}

/**
 * Legacy spareslist_update.jsp / SparesEngg_PageController_update: all pending spares (optional division filter).
 */
export function isSparesAllDivisionsRole(role: string | null | undefined): boolean {
  const r = normalizeAppRole(role);
  return r === "ADMIN" || r === "VICE_CHANCELLOR" || r === "SERVICE_COORDINATOR";
}

export function canDeleteSparesForRole(role: string | null | undefined): boolean {
  const r = normalizeAppRole(role);
  return r === "ADMIN" || r === "VICE_CHANCELLOR";
}

export function canCreateSparesForRole(role: string | null | undefined): boolean {
  const r = normalizeAppRole(role);
  return r === "ADMIN" || r === "ENGINEER" || r === "VICE_CHANCELLOR";
}
