# SC Closed Product (`closedproduct.jsp`)

## Legacy purpose

**SC Closed Product** lists service rows that have left the service-centre repair loop (ship-from-SC and repaired-board stock dates are set) but are **not** yet handed off commercially (`ship_date_from_commercial` is still empty). It is **not** the same as **Under repair** (which requires `repaired_brd_stk_date IS NULL`).

## Legacy flow

| Step | Artifact | Behaviour |
|------|----------|-----------|
| 1 | `closedproduct.jsp` | Role-gated shell; title **SC Closed Product**; DataTables grid; **Export** posts to `ExportClosedController`. |
| 2 | `Closed_Controller` (GET, server-side DataTables) | JSON for grid. **Filter SQL:** `ship_dt_frm_ser_cntr IS NOT NULL AND repaired_brd_stk_date IS NOT NULL AND ship_date_from_commercial IS NULL`. Legacy used **Sc ref prefix**; migrated API uses **substring** (case-insensitive): `LIKE '%' + value + '%'` on `sc_ref_no`. **Global search:** `def_gir_no` / `frn_no` contains. **Sort:** configurable column (legacy maps order column index to a fixed `cols[]` list). |
| 3 | `ClosedDao.ClosedExcel` / `ExportClosedController` | Excel download using the **same base filter** as the grid (very wide column set in legacy `.xls`). |

## Grid columns (legacy `closedproduct.jsp`)

| # | Column |
|---|--------|
| 1 | Id |
| 2 | Entry Date |
| 3 | Sc RNo |
| 4 | Sc Eng |
| 5 | Frn No |
| 6 | Region |
| 7 | Eng (RA) |
| 8 | Cust Name |
| 9 | Model |
| 10 | Unit Status |
| 11 | Def Mod / brd name |
| 12 | Def Gir No |
| 13 | Type of work |
| 14 | **Days taken to complete** |

**Note:** Legacy grid has **no Final Remarks** column (unlike Under repair / Pending FRN).

## “Days taken to complete” (legacy)

`Closed_Controller` computes **calendar days from `ser_centre_received_date` to today** (same day-diff idea as **PDays** on other queues). The REST API exposes this as `pendingDays` on `ServiceMasterDTO` for reuse.

## REST mapping (migrated stack)

| Operation | Method | Path |
|-----------|--------|------|
| Paged list | GET | `/api/services/sc-closed` — query: `page`, `size`, `scRef`, `search`, `sort` |
| Excel export | GET | `/api/services/export/sc-closed` — query: `scRef`, `search` |

## Frontend

| Route | File |
|-------|------|
| `/dashboard/sc-closed` | `schiller-frontend/src/app/(dashboard)/dashboard/sc-closed/page.tsx` |

Excel export uses the **14 grid columns** (no Final Remarks), last column header **Days taken to complete**, aligned with the legacy grid (not the very wide legacy `ClosedDao` dump).

## Related legacy

- **Under repair** — opposite on `repaired_brd_stk_date` (must be `NULL`).
- **Escalation** — `EscalationClosedPrConroller` / `EscalationClosedDao` can email `closedproduct.jsp` context; not migrated in this slice.
