# Legacy menu matrix

Maps legacy JSP sidebar links to new Next.js routes and migration status.

## Vice Chancellor (`VPDashboard.jsp`)

| # | Legacy JSP | New route | Status | Notes |
|---|------------|-----------|--------|-------|
| 1 | `index.jsp` (Dashboard) | `/dashboard/vp` | Done | VP hub landing |
| 2 | `ServiceList.jsp` | `/dashboard/services` | Done | All divisions; no create for VP |
| 3 | `under_repair.jsp` | `/dashboard/under-repair` | Done | |
| 4 | `pending_FRN.jsp` | `/dashboard/pending-frn` | Done | VP all-divisions scope |
| 5 | `ob_pending.jsp` | `/dashboard/ob-pending` | Done | |
| 6 | `closedproduct.jsp` | `/dashboard/sc-closed` | Done | VP all-divisions scope |
| 7 | `New_ClosedProduct.jsp` | `/dashboard/new-closed` | Done | |
| 8 | `ScarpList.jsp` | `/dashboard/scrap-list` | Done | |
| 9 | `CallListAdmin.jsp` | `/dashboard/call-list` | Done | VP division filter + all-divisions API |
| 10 | `ClosedCalls.jsp` | `/dashboard/closed-calls` | Done | |
| 11 | `PendingActListAdmin.jsp` | `/dashboard/pending-activity` | Done | VP division filter + all-divisions API |
| 12 | `ClosedActivity.jsp` | `/dashboard/closed-activity` | Done | |
| 13 | `PRFOB_AdminList.jsp` | `/dashboard/prf-ob-admin` | Partial | List/export/delete; no update API |
| 14 | `nonSaleAdminList.jsp` | `/dashboard/non-salable-admin` | Partial | List/export/delete; no update API |
| 15 | `SalablesList.jsp` | `/dashboard/salables` | Done | |
| 16 | `BIRAdminList.jsp` | `/dashboard/bir-admin` | Partial | List/export/delete; no update API |
| 17 | `ClosedBIRList.jsp` | `/dashboard/bir-closed-admin` | Done | |
| + | `PRFOB_AdminList_closed.jsp` | `/dashboard/prf-ob-closed` | Done | Included in VP nav (not in legacy VP sidebar) |

**Blocked for VP (master-data / admin):** employees, divisions, dealers, settings, escalations, spares, admin KPI dashboard (`/dashboard`).

**Tickets:** VP-001 (menu matrix), VP-002 (auth/nav/API scope) — completed on branch `feature/vp-legacy-parity`.
