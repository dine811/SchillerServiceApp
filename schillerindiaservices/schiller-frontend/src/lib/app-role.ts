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

export function isViceChancellor(role: string | null | undefined): boolean {
  return normalizeAppRole(role) === "VICE_CHANCELLOR";
}

/** Legacy VP + admin: all divisions on operational list screens. */
export function isVpOperationalPrivileged(role: string | null | undefined): boolean {
  const r = normalizeAppRole(role);
  return r === "ADMIN" || r === "VICE_CHANCELLOR";
}

/** Legacy PrfOb_Update.jsp: VP may delete PRF/OB rows. */
export function canVpDeletePrfOb(role: string | null | undefined): boolean {
  return isVpOperationalPrivileged(role);
}

/** Legacy ServiceForm2.jsp: VP may update service records. */
export function canVpUpdateService(role: string | null | undefined): boolean {
  return isVpOperationalPrivileged(role);
}

/** Legacy VP had no employee/division/dealer master screens. */
export function canVpAccessMasterData(role: string | null | undefined): boolean {
  return normalizeAppRole(role) === "ADMIN";
}

/** Legacy ServiceList.jsp: VP views and updates; no create-new entry. */
export function canVpCreateService(role: string | null | undefined): boolean {
  const r = normalizeAppRole(role);
  return r === "ADMIN" || r === "ENGINEER";
}
