# Engineer Service Module Migration

## Scope

This document captures the migration of legacy engineer service list behavior from:

- `WebContent/ServiceListEngg.jsp`
- `WebContent/engineerdashboard.jsp`

to the new stack:

- Next.js frontend (`/dashboard/services`)
- Spring Boot backend (`/api/services`, `/api/services/export`)

## Legacy Behavior (ServiceListEngg.jsp)

### Entry points and access

- Engineer dashboard menu points to `ServiceListEngg.jsp`.
- Session auth check: redirects to login if no session user.
- Role-aware dashboard include:
  - Admin -> `admindashboard.jsp`
  - Others -> `engineerdashboard.jsp`

### Grid source and filters

- Grid is DataTables server-side.
- Ajax source is `Service_Controller` (same endpoint as admin list).
- Engineer visibility is enforced server-side in `Service_Controller`:
  - Admin: all `service_master` rows
  - Non-admin: rows where `division = employee.division`

### Actions visible in engineer grid

- Engineer list row action:
  - `JobSheetController?action=edit&id=...` only
- No inline edit/update/delete actions in engineer grid.

### Export behavior

- Export form posts to `ServiceControllerEngg` with `from` and `to`.
- Export logic (`ServiceDao.ExcelForEngg`) applies:
  - `entry_date between from and to`
  - `division = employee.division`

### Add record behavior

- Engineer screen keeps `Add New Record` button (`ServiceForm.jsp`).

## New-System Mapping

### Frontend mapping

- Legacy `ServiceListEngg.jsp` -> Next page `src/app/(dashboard)/dashboard/services/page.tsx`
- Menu already available from engineer dashboard navigation.

### Backend mapping

- Legacy `Service_Controller` (list JSON) -> `GET /api/services`
- Legacy `ServiceControllerEngg` export -> `GET /api/services/export`

## Implementation Decisions

1. Keep one service list page for admin + engineer.
2. Enforce engineer data scope in backend (division based).
3. Mirror legacy row actions:
   - Engineer: Job Sheet only
   - Admin: Edit / Update / Delete + Job Sheet
4. Keep Add New Record visible for engineer (legacy parity).

## Implemented Changes

### Backend

- `ServiceMasterController#getAllServices` now reads authenticated user and role.
- For non-admin users, list/search is restricted to the logged-in user division.
- `ServiceExportController#exportExcel` now scopes export by role:
  - Admin: full dataset
  - Non-admin: only user division rows
- `ServiceMasterService` added role-aware methods:
  - `findAllForUser(...)`
  - `searchForUser(...)`
  - `resolveDivision(...)`
- `ServiceRepository` added division-scoped queries:
  - `findByDivisionIgnoreCase(...)`
  - `searchServicesByDivision(...)`

### Frontend

- `services/page.tsx` now loads `/api/auth/me` to detect role.
- Engineer role UI changes:
  - Title switched to `Service List` (legacy label alignment)
  - Row actions show Job Sheet only
  - Edit/Update/Delete hidden for engineer
- Add New Record and Export remain available.

## Result

- Engineer users now see only their division's service records.
- Engineer list action model matches the legacy service module.
- Admin behavior remains unchanged for full list and management actions.
