# Repair Team module migration design

## Purpose and source inventory

This design migrates repair initiation, the PFRN/OB/UR pending queues, the completed queue, repair updates, exports, role access, and synchronization back to `service_master`.

Legacy sources: `repairDashboard.jsp`, `repairList.jsp`, `repairList_OB.jsp`, `repairList_UR.jsp`, `repairList_Closed.jsp`, `RepairActUpdate.jsp`, all `RepairAct*`, `RepairUpdate*`, and repair export controllers, `RepairActivityDao`, and `Repair_master`.

## Legacy behavior

### Navigation and queues

The `repairteam` dashboard links PFRN, OB, UR, Closed Repair, Call Register, and Closed Calls.

| Screen | Category | Status |
|---|---|---|
| Repair List PFRN | `PFRN` | `pending` |
| Repair List OB | `OB` | `pending` |
| Repair List UR | `UR` | `pending` |
| Closed Repair List | all | `completed` |

Repair lists are not division-scoped. The controller's `OEM` division variable is unused.

Visible columns are Entry Date, Division, SC Ref No, Def GIR No, Category, Model, Def Board/Module Name, No. of Days, and Status. Pending lists add Update. Pending days are calculated live from `entry_date`; completed rows display stored days. Global search matches division, SC reference, or defective GIR. Lists use server paging (default 10, maximum 100) and default to ID descending.

### Repair initiation

Engineer queues invoke `RepairUpdateController?action=edit&cat=<PFRN|OB|UR>&id=<serviceId>`. When the service has `repair_status = NR`, legacy code inserts a pending repair populated from the service, then changes the service to `RP`. Queue UI subsequently displays `RP`, then `RC` after completion.

### Repair update

Repair Team sees identity/header fields read-only. Editable fields are repaired by (required), components used, technical remarks, final remarks, and status (`pending` or `completed`, required). Saving recalculates elapsed days.

- Pending: `finished_date` remains empty and service repair status remains `RP`.
- Completed: `finished_date` becomes today, service repair status becomes `RC`, components copy to `service_master.components_usedfor_repair`, and `report_type` becomes `repairlist`.

### Export

Exports use an inclusive entry-date range. Pending exports retain their category; closed export includes all categories. The target `.xlsx` columns are: ENTRY DATE, DIVISION, SC REF NO, DEF GIR NO, CATEGORY, MODEL, DEF BOARD/MODULE NAME, TECH REMARKS, COMPONENTS USED TO REPAIR, FINISHED DATE, REPAIRED BY, FINAL REMARKS, STATUS, and NO OF DAYS.

## Target architecture

### Persistence

Use `repair_master` from `V23__repair_master_legacy_shape.sql`. Add a JPA entity linked by `ser_id` to its service. Add a partial unique database index preventing more than one pending repair per service while preserving completed history. Represent category and status with validated enums. Completed repairs are immutable. All repair/service mutations use one Spring transaction.

### REST API

- `GET /api/repairs`: pageable; validated `category`, `status`, `from`, `to`, `division`, `scRefNo`, `search`, and sort. Search covers division/SC reference/GIR. Dates are inclusive and ID is a deterministic secondary sort.
- `GET /api/repairs/{id}`: persisted fields, service ID, live pending days, and stored completion days.
- `POST /api/repairs/from-service/{serviceId}`: validated category; verifies service visibility, queue eligibility, and `NR`; atomically creates one pending repair and sets `RP`; rejects duplicates/ineligible records.
- `PUT /api/repairs/{id}`: accepts only repaired-by, components, technical/final remarks, and status. Pending retains `RP` without clearing service data. Completion atomically records finish date/days and updates service to `RC` with components/report type. Completed rows reject further changes.
- `GET /api/repairs/export`: same filters/scope; streams the 14-column workbook without a server temp file.

### Authorization

| Capability | ADMIN | REPAIR_TEAM | ENGINEER |
|---|:---:|:---:|:---:|
| List/detail/export | Yes | Yes | No |
| Update pending repair | Yes | Yes | No |
| Initiate eligible service repair | Yes | No | Yes, within service scope |

Repair Team retains legacy all-division visibility. Engineer initiation follows existing service division/assignment scope.

### Next.js routes

- `/dashboard/repair` — pending PFRN
- `/dashboard/repair-ob` — pending OB
- `/dashboard/repair-ur` — pending UR
- `/dashboard/repair-closed` — completed repairs
- `/dashboard/repair/[id]/edit` — pending update

The routes share a typed table/filter component with loading, empty, error, search, paging, and validated date-range export states. Repair Team defaults to `/dashboard/repair`; navigation includes all four repair routes plus existing Call Register and Closed Calls. Eligible service queues expose initiation and `NR` → `RP` → `RC` state.

## Defects intentionally not reproduced

- Repair delete wrongly targets `pendingactmaster`.
- SQL concatenation, swallowed exceptions, and incorrect string comparisons.
- Missing authorization and trusted client category/service IDs.
- Non-transactional, duplicate-prone initiation and update.
- Ignored editable header fields and missing hidden inputs in Admin/VP form variants.
- Pending update clearing unrelated service fields.
- Completed-to-pending reopening.
- Two hardcoded technician choices.
- Public server export files and injection-prone export SQL.

## Parity and acceptance matrix

| Legacy capability | Modern acceptance criterion |
|---|---|
| PFRN/OB/UR pending queues | Each route returns only its category plus `pending`, matching columns with live days. |
| Closed queue | All completed categories appear read-only with stored days. |
| Search/filter/paging/sort | Validated filters produce deterministic results matching legacy intent. |
| Initiation | Eligible `NR` service creates exactly one pending repair and becomes `RP` atomically. |
| Duplicate/concurrent initiation | Database and service reject a second active repair. |
| Pending update | Fields persist, finish date stays empty, service remains `RP`, and unrelated data is preserved. |
| Completion | Repair and service atomically become completed/`RC`; date, days, components, and report type are correct. |
| Invalid transition | Completed repairs cannot be reopened or modified. |
| Authorization | Role and object scope are enforced server-side. |
| Export | Same filters and exact 14-column contract are used. |
| Navigation | Repair Team lands on PFRN and reaches all repair and call pages. |

## Verification

Backend tests cover every category/status queue, repository filters/date bounds/search/sort/paging, eligibility, duplicate detection and database-race translation, state synchronization, immutable completion, roles, engineer scope, and export columns. The partial unique PostgreSQL index is the production concurrency guard. Frontend verification covers all five routes, filters/export/paging, error/empty states, update validation, completion, route guards, navigation, and service initiation labels through changed-file lint, TypeScript, production build, and live smoke testing. The frontend currently has no component-test harness; that is a known test-automation limitation, not a parity gap. Pre-existing full-project lint failures remain separately documented.
