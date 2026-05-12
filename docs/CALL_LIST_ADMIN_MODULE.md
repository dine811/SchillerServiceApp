# Call list (admin) — `CallListAdmin.jsp`

## Legacy behaviour

- **Menu:** Admin (and VP) dashboard → **Activity registers** → **Call Register** (`admindashboard.jsp` → `CallListAdmin.jsp`).
- **Title:** “Call Registry” / page header “Call List”.
- **Data source:** Table `callregister` (bean `Callregister`).
- **Grid (DataTables):** `CallReg_AdminPageController` — **only rows with `status_type = 'Pending'`** (case-insensitive match in the new API).
- **Columns (display):** Division, Entry Date, Call Date, Sc Engg (resolved via employee master), Engineer, Model, Type of Call, Remarks, Status; Id hidden in UI; **Action** column injected JS: Update / Delete links to `CallRegisterController`.
- **Export:** Form POST `ExportCall_adminController` with **Division**, **From date**, **To date** — note on page: *“From and to date based on call date”*. Export logic in `Export_CallRegDao.AdminCallRegList` varies by division option (all vs one division vs pending-only subset in legacy).
- **Update:** Not migrated in this iteration; **Delete** is exposed via REST `DELETE /api/call-register/{id}`.

## New stack mapping

| Legacy | New |
|--------|-----|
| `CallReg_AdminPageController` | `GET /api/call-register/pending` (pagination, `division`, `search`) |
| `ExportCall_adminController` / `Export_CallRegDao` | `GET /api/call-register/export/pending` (`division`, `search`, `from`, `to` ISO dates; date filter applied in export service after parsing `call_date` strings) |
| `CallRegisterController?action=delete` | `DELETE /api/call-register/{id}` |
| `CallListAdmin.jsp` | Next.js `/dashboard/call-list` |

## Database

- Entity: `CallRegister` → table `callregister` (columns aligned with legacy bean; `remarks` mapped with length 2000 for long text).
- **Closed calls** (separate screen) use table **`callregister_demo`** in the legacy app; see [CLOSED_CALLS_MODULE.md](CLOSED_CALLS_MODULE.md).
- Ensure the `callregister` table exists in the target PostgreSQL database (migrated from legacy).

## Frontend

- Route: **`/dashboard/call-list`**
- Navigation: **Activity registers** → **Call register** (`navigation.ts`).
- Filters: division (optional), search (engineer / type of call / status text).
- Export: optional ISO **from** / **to** for call-date narrowing on export.
