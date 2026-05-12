/**
 * Legacy PRFOB_AdminList.jsp / PRFOB_AdminList_closed.jsp: Service Coordinator sees Update only;
 * other roles see Update | Delete. Mirrors session `logrole` when available client-side.
 */
export function showPrfobDelete(): boolean {
  if (typeof window === "undefined") return true;
  const raw = sessionStorage.getItem("logrole");
  if (raw == null || raw === "") return true;
  return raw.trim().toLowerCase() !== "servicecoordinator";
}
