# Closed calls — `ClosedCalls.jsp`

## Legacy behaviour

- **Menu:** Admin, VP, PS, Engineer, Repair dashboards → **Closed Calls** → `ClosedCalls.jsp` (title “Call List” / call details grid).
- **Data:** Legacy **`ClosedCalls_Controller` reads completed rows from `callregister_demo`**, not `callregister`. (Admin pending list uses `callregister`.) The new API matches this: completed list / export / counts use **`callregister_demo`**; pending uses **`callregister`**.
- **Grid (DataTables server-side):** `ClosedCalls_Controller` — pagination, global search on engineer / type of call / status; optional column filter on division (admin path).
- **Roles:**
  - **Admin / ViceChancellor:** all divisions’ completed rows; **Action** = Update | Delete (`CallRegisterController?action=edit|delete&pg=clsd`).
  - **Other roles (e.g. Engineer):** restricted to the logged-in user’s division (`CallRegisterDao.getDivName`); **no Action** column in JSP for non-admin.
- **Export:** POST `ExportClosedCalls_Controller` — Division (admin: “All Division” = `1` loads all completed in legacy DAO), **from / to** dates on **call date** (`Export_CallRegDao.ClosedCalls`).

## New stack mapping

| Legacy | New |
|--------|-----|
| `ClosedCalls_Controller` (JSON grid) | `GET /api/call-register/completed` (`division`, `search`, pagination, `sort`) |
| `ExportClosedCalls_Controller` | `GET /api/call-register/export/completed` (`division`, `search`, `from`, `to` ISO dates; call-date filter in export service) |
| `CallRegisterController?action=delete` | `DELETE /api/call-register/{id}` (same as pending list) |
| `ClosedCalls.jsp` | Next.js `/dashboard/closed-calls` |
| *(diagnostics)* | `GET /api/call-register/summary` → `{ total, pending, completed }` |

## Parity notes

- **Table:** Completed rows are read from **`callregister_demo`** (same as legacy). Rows must have `status_type` matching **Completed** / **Complete** (case-insensitive).
- **Empty grid:** If `callregister_demo` has no matching rows, check `SELECT COUNT(*), status_type FROM callregister_demo GROUP BY status_type;` after restoring your dump.
- **Role-based division lock** for engineers is not enforced in the REST API yet; the UI exposes the same division filter as the call-register page. When JWT + role are wired, restrict `completed` for non-admin users to their division server-side.
- **Update** (`CallRegisterController?action=edit`) is not migrated in this iteration.
