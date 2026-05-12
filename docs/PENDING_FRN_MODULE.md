# Pending FRN module — legacy behaviour and migration notes

This document describes the legacy **Pending FRN** queues (`pending_FRN.jsp` / `PendingFRN_Controller`, and engineer **`emp_PendingFRN.jsp`** / **`EmpPFRNController`**), SQL and filters, and how they map to the Spring Boot + Next.js stack.

---

## 1. Purpose (what “Pending FRN” means)

The list is a **filtered slice of `service_master`** where:

- **`ship_dt_frm_ser_cntr` is null** — ship-from–service-centre has **not** been recorded.
- **Unit status** (via `dropdownmaster` / `dropdown_master` join on `unit_status` = dropdown id) has **`ddvalue` not in (`OW`, `LAMC`)**.

So **Pending FRN** and **OB Pending** are **mutually exclusive** when ship-from-SC is still empty:

| Queue | Unit status (`ddvalue` via join) | `ship_dt_frm_ser_cntr` |
|--------|----------------------------------|-------------------------|
| **Pending FRN** | **Not** OW and **not** LAMC | `IS NULL` |
| **OB Pending** | **OW** or **LAMC** | `IS NULL` |

**PDays** (legacy “Pend Days”): calendar days from **`ser_centre_received_date`** to **today** (same idea as **Under repair** / **OB Pending** grids).

---

## 2. Legacy artefacts

| Concern | Artifact | Notes |
|--------|-----------|--------|
| Admin / shared DataTables JSON | `PendingFRN_Controller` → `/PendingFRN_Controller` | **GET** — `aaData`, `iTotalRecords`, `iTotalDisplayRecords`; **no** division filter |
| Engineer DataTables JSON | `EmpPFRNController` → `/EmpPFRNController` | **GET** — same queue rules **plus** `sm.division =` logged-in engineer’s division (`EmployeeDao.getdivEmpName`); extra **`repair_status`** column for SR/RP/RC UI |
| Engineer page shell | `emp_PendingFRN.jsp` | Session gate; `admindashboard.jsp` or `engineerdashboard.jsp`; DataTables `serverSide: true`, `ajax: "EmpPFRNController"`; **Update** → `PendingFRNController?action=edit...`; **Repair** → `RepairUpdateController?cat=PFRN...` when SR |
| Engineer export | **POST** `EmpPFRNController` → `EmpPFRNDao.pendingFRNExcel` | Wide legacy Excel; division-scoped |
| Admin export (legacy) | `ExportPFRNController` / `PendingFRNDao` | Mapped in `web.xml`; grid-aligned export |
| Escalation | `PendingFRNEscalutionController`, `PendingFRNEscalutionDao*`, mail jobs | Out of scope for list page parity |

**Note:** `WebContent/PendingFRN.jsp` is an older form-heavy page; the **authoritative grid query** for the admin list is **`PendingFRN_Controller`** (same SQL shape as below).

---

## 3. SQL and filters

**Base list / count** (admin `PendingFRN_Controller`):

```sql
FROM service_master sm
INNER JOIN dropdownmaster dm ON sm.unit_status = dm.dd_id
WHERE dm.ddvalue NOT IN ('OW','LAMC')
AND sm.ship_dt_frm_ser_cntr IS NULL
```

**Engineer** (`EmpPFRNController`): append

```sql
AND sm.division = '<division from session employee>'
```

**Column search (SC ref)** — legacy prefix on column index 1:

- `columns[1][search][value]` → `LOWER(sc_ref_no) LIKE LOWER('<value>%')` when non-empty.  
- **Migrated API** uses a dedicated `scRef` query param with substring `LIKE '%value%'` (aligned with other queues).

**Global / keyword search**

- **Admin** `PendingFRN_Controller`: `def_gir_no` OR `frn_no` contains term (`search[value]`).
- **Engineer** `EmpPFRNController`: also **`sc_ref_no`** contains term.

**Migrated backend** (`ServiceRepository.findPendingFrn`): keyword matches **FRN**, **Def GIR**, or **Sc RNo**; optional **division** parameter (non-admin users: JWT user’s employee division, same idea as engineer JSP).

---

## 4. Grid columns (legacy engineer JSP → UI)

`emp_PendingFRN.jsp` **thead**: Id, Entry Date, Sc RNo, Sc Eng, Frn No, Region, Engineer, Cust Name, Model, Unit St, Def Mod / brd name, Def Gir No, Final Remarks, Type of work, Pend Days, **Update**, **Repair**.

- **Update** — link to legacy service edit (`PendingFRNController?action=edit&id=...`).
- **Repair** — driven by `repair_status` from DB:
  - **`RP`** / **`RC`** — static label in grid.
  - **`NR`** or other — **SR** link to `RepairUpdateController?action=edit&cat=PFRN&id=...`.

**New stack:** **Update** → `/dashboard/services/{id}/edit`; **SR** → `/dashboard/services/{id}/update` (repair / stock workflow). **`repair_status`** is exposed on `ServiceMaster` / DTO as `repairStatus`.

---

## 5. New stack mapping (implemented)

| Layer | Location / behaviour |
|--------|------------------------|
| Entity / DTO | `repair_status` → `ServiceMaster.repairStatus`, `ServiceMasterDTO.repairStatus` |
| Repository | `ServiceRepository.findPendingFrn(..., admin, div, empId)` — **admin**: all rows; **engineer**: non-blank division match on `service_master.division` **or** `sc_engineer_id` / `ra_engineer_id` = logged-in `emp_id` |
| API list | `GET /api/services/pending-frn` — **ROLE_ADMIN**: all rows; **others**: `findPendingFrnForUser(username, …)` |
| API export | `GET /api/services/export/pending-frn` — same scope as list |
| Service logic | `ServiceMasterService.findPendingFrnForUser` |
| Frontend | `/dashboard/pending-frn` — filters, 15 data columns + **Update** + **Repair**; engineer hub: `/dashboard/engineer` → Pending FRN |

---

## 6. Related documents

- [OB_PENDING_MODULE.md](./OB_PENDING_MODULE.md) — complement queue (OW/LAMC, same ship-null rule).

---

## 7. Document history

- 2026-04-09 — Initial write: engineer vs admin paths, SQL, repair column, new stack file references.
