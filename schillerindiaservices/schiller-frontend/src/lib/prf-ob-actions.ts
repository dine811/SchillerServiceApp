import { showPrfobDeleteForRole } from "@/lib/app-role";
import { decodeRoleFromJwt } from "@/lib/auth";
import { getClientAuthToken } from "@/lib/api";

/**
 * Legacy PRFOB_AdminList.jsp: Service Coordinator sees Update only; others see Update | Delete.
 */
export function showPrfobDelete(roleFromMe?: string | null): boolean {
  if (roleFromMe != null && roleFromMe !== "") {
    return showPrfobDeleteForRole(roleFromMe);
  }
  if (typeof window === "undefined") return true;
  const fromJwt = decodeRoleFromJwt(getClientAuthToken() ?? undefined);
  if (fromJwt) return showPrfobDeleteForRole(fromJwt);
  const raw = sessionStorage.getItem("logrole");
  if (raw == null || raw === "") return true;
  return raw.trim().toLowerCase() !== "servicecoordinator";
}
