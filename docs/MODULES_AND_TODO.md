# Schiller India Services (SIS) — Module list & backlog

Legacy entry: `schillerindiaservices/WebContent/index.jsp` reads `session.getAttribute("logrole")` and includes **exactly one** of seven dashboards (see table below). The modules section reflects the full **WebContent** JSP surface plus the modern **Next.js + Spring Boot** stack.

---

## User types (roles) — `index.jsp`

There are **7 distinct user types** in `index.jsp` (six explicit `logrole` checks plus one **default** branch). These are **application roles** (what menu/dashboard the user gets), not separate database tables in this file—operationally you may align them with departments or job functions.

| # | Session `logrole` value (code) | Included dashboard | Typical label |
|---|-------------------------------|-------------------|-----------------|
| 1 | `admin` | `admindashboard.jsp` | Administrator |
| 2 | `Engineer` | `engineerdashboard.jsp` | Field engineer |
| 3 | `FQC` | `FQCdashboard.jsp` | FQC (quality) |
| 4 | `ViceChancellor` | `VPDashboard.jsp` | Vice President / leadership |
| 5 | `servicecoordinator` | `ServiceCoorDashBoard.jsp` | Service coordinator |
| 6 | `repairteam` | `repairDashboard.jsp` | Repair team |
| 7 | *(any other / unset role)* | `PSdashboard.jsp` | Product service (PS) — **default** fallback |

**Source:** `schillerindiaservices/WebContent/index.jsp` (lines 47–80).

---

## Application modules

| # | Module | Scope (legacy / notes) |
|---|--------|-------------------------|
| 1 | **Authentication & session** | Login, logout, registration, role gate on `index.jsp` |
| 2 | **Role dashboards** | Admin, Engineer, FQC, Vice Chancellor, Service Coordinator, Repair team, PS (default) — each loads its own nav |
| 3 | **Organization** | Company, branch |
| 4 | **Divisions / products** | Product lists/forms, models, part numbers, product status |
| 5 | **Engineers** | Employee lists/forms, engineer-specific FRN/OB/UR lists |
| 6 | **Dealers & regions** | Dealer CRUD, product–dealer links |
| 7 | **Services (core)** | Service list/filters, forms, job sheet, under repair / pending FRN / OB / closed / scrap |
| 8 | **Calls** | Call register, admin/engineer lists, closed calls, updates |
| 9 | **Pending / closed activity** | Pending and closed activity lists and update forms |
| 10 | **PRF / OB** | PRF/OB registers, closed lists, updates |
| 11 | **Non-saleable / salables** | Non-sale and salable workflows (admin + engineer) |
| 12 | **BIR** | BIR lists, closed BIR, admin/engineer variants |
| 13 | **Repairs (repair team)** | Repair lists (PFRN, OB, UR, closed), repair dashboard |
| 14 | **Spares** | Spare lists, requests, supplier lookups, engineering lists |
| 15 | **Escalation & mail** | Auto-escalation mail entry, automail lists, SMTP/email settings |
| 16 | **Masters / settings** | Dropdown values, settings page, email settings |
| 17 | **Supplier** | Supplier list/form |
| 18 | **Misc dashboards** | VP, FQC, service coordinator, admin, engineer, PS dashboards |
| 19 | **Errors / utilities** | Error pages, search, misc helpers |

---

## Module documentation

| Topic | Document |
|-------|----------|
| Under repair (legacy flow, filters, REST mapping) | [UNDER_REPAIR_MODULE.md](UNDER_REPAIR_MODULE.md) |
| OB Pending (`ob_pending.jsp`, OW/LAMC + no ship-from-SC, migration checklist) | [OB_PENDING_MODULE.md](OB_PENDING_MODULE.md) |
| SC Closed Product (`closedproduct.jsp`, SC closed + no commercial ship date) | [CLOSED_PRODUCT_MODULE.md](CLOSED_PRODUCT_MODULE.md) |
| Closed Product (`New_ClosedProduct.jsp`, commercial ship date recorded) | [NEW_CLOSED_PRODUCT_MODULE.md](NEW_CLOSED_PRODUCT_MODULE.md) |
| Call list admin (`CallListAdmin.jsp`, pending `callregister` rows, export) | [CALL_LIST_ADMIN_MODULE.md](CALL_LIST_ADMIN_MODULE.md) |
| Closed calls (`ClosedCalls.jsp`, completed `callregister`, export) | [CLOSED_CALLS_MODULE.md](CLOSED_CALLS_MODULE.md) |

---

## Modern stack (current)

| Layer | Location | Notes |
|-------|----------|--------|
| **Frontend** | `schillerindiaservices/schiller-frontend` | Next.js (dashboard, services, employees, divisions, dealers, escalations, reports, settings, spares) |
| **Backend** | `schiller-backend` | Spring Boot REST (services, employees, dealers, branches, models, suppliers, spare parts, dropdowns, regions, mail IDs, product master, export) |
| **Legacy** | `schillerindiaservices/WebContent` | JSP application (~125 pages); parity with Next is partial |

---

## TODO backlog (remaining project)

### Foundation

- [ ] Document **role → allowed modules** (mirror the seven `index.jsp` branches).
- [ ] **Auth**: JWT or session strategy for Next + API; align with legacy roles.
- [ ] **API coverage map**: legacy JSP clusters → REST resources + Next routes.

### Admin & settings

- [ ] Next **admin** area: organization, masters, settings parity.
- [ ] Replace or embed legacy **Settings** (dropdowns, email/SMTP, automail IDs).

### Core operations

- [ ] **Services**: parity with legacy list filters, job sheet, full field set.
- [ ] **Calls** register + closed (admin vs engineer).
- [ ] **Activity** pending/closed (admin + engineer).
- [ ] **PRF/OB**, **non-sale / salables**, **BIR** lists and workflows.

### Secondary

- [ ] **Repair team** flows.
- [ ] **Spares** end-to-end.
- [ ] **Reports**: wire to real APIs/exports.

### Cutover

- [ ] Deprecate or redirect legacy JSP routes per module after Next parity.
- [ ] Regression checklist per **role** (same as seven dashboard includes).

---

*Last updated from repository analysis; adjust priorities with the product owner.*
