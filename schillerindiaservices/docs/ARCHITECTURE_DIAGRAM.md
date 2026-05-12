# Schiller India Services - New Migrated Codebase Architecture

**Document type:** High-level architecture diagram  
**Scope:** Current migrated Next.js + Spring Boot codebase and completed/partially migrated modules  
**Related docs:** `MIGRATION_STATUS.md`, `ADMIN_LEGACY_MODULES.md`, `ENGINEER_SERVICE_MODULE_MIGRATION.md`, `AUTHENTICATION_AUTHORIZATION_IMPLEMENTATION_PLAN.md`  
**HTML version:** [ARCHITECTURE_DIAGRAM.html](./ARCHITECTURE_DIAGRAM.html)  
**Last updated:** April 2026

---

## 1. System context

```mermaid
flowchart LR
  Users["Business users<br/>Admin, Engineer, FQC, VP,<br/>Service Coordinator, Repair Team,<br/>Product Support"]

  Browser["Browser"]

  Frontend["Next.js Frontend<br/>schiller-frontend<br/>Dashboard UI, routing, role nav"]

  Backend["Spring Boot Backend<br/>schiller-backend<br/>REST APIs, services, exports"]

  Database[("PostgreSQL Database<br/>Flyway-managed schema<br/>legacy-aligned tables")]

  Legacy["Legacy JSP Application<br/>WebContent/*.jsp<br/>reference / pending modules"]

  Exports["Excel Downloads<br/>Apache POI .xlsx"]

  Users --> Browser
  Browser --> Frontend
  Frontend --> Backend
  Backend --> Database
  Backend --> Exports
  Legacy -. migration reference .-> Frontend
  Legacy -. migration reference .-> Backend
```

The new application separates the legacy JSP monolith into a **Next.js dashboard frontend**, a **Spring Boot REST API**, and a **PostgreSQL database** managed through Flyway migrations. Legacy JSP files remain the reference for parity, pending modules, and business-rule validation.

---

## 2. Container / layer view

```mermaid
flowchart TB
  subgraph Client["Client Layer"]
    Browser["User Browser"]
  end

  subgraph FE["Frontend - schiller-frontend"]
    NextApp["Next.js App Router<br/>src/app/(dashboard)/dashboard"]
    RoleRouting["Role routing<br/>src/lib/auth.ts"]
    Navigation["Role navigation<br/>src/config/navigation.ts"]
    Pages["Dashboard pages<br/>services, calls, activity,<br/>PRF/OB, BIR, non-salable,<br/>spares, reports"]
    ApiClient["API client / fetch services"]
  end

  subgraph BE["Backend - schiller-backend"]
    Controllers["REST Controllers<br/>/api/*"]
    Services["Service Layer<br/>business rules, filters, exports"]
    Repositories["JPA Repositories<br/>queries and specifications"]
    Security["Spring Security / JWT model<br/>role and data-scope hardening"]
    Excel["Excel Export Layer<br/>Apache POI"]
  end

  subgraph DB["Data Layer"]
    Postgres[("PostgreSQL")]
    Flyway["Flyway migrations<br/>V10+ service modules,<br/>V12-V17 BIR/non-salable/spares fixes"]
  end

  Browser --> NextApp
  NextApp --> RoleRouting
  NextApp --> Navigation
  NextApp --> Pages
  Pages --> ApiClient
  ApiClient --> Controllers
  Controllers --> Security
  Controllers --> Services
  Services --> Repositories
  Services --> Excel
  Repositories --> Postgres
  Flyway --> Postgres
```

> Note: The architecture target is JWT + role-aware access. The remaining todo list keeps auth hardening as a P0 item so route permissions, endpoint guards, and data scoping are verified before cutover.

---

## 3. Role routing and dashboard access

```mermaid
flowchart LR
  Login["Login / authenticated session"]
  Role["Normalized app role"]

  Admin["ADMIN<br/>/dashboard<br/>full dashboard access"]
  Engineer["ENGINEER<br/>/dashboard/engineer<br/>engineer service/activity view"]
  FQC["FQC<br/>/dashboard/fqc<br/>activity + non-salable + BIR"]
  VP["VICE_CHANCELLOR<br/>/dashboard/reports<br/>partial executive access"]
  Coordinator["SERVICE_COORDINATOR<br/>/dashboard/call-list<br/>partial coordinator access"]
  Repair["REPAIR_TEAM<br/>/dashboard/under-repair<br/>partial repair access"]
  PS["PRODUCT_SUPPORT<br/>/dashboard/prf-ob-admin<br/>partial product support access"]

  Login --> Role
  Role --> Admin
  Role --> Engineer
  Role --> FQC
  Role --> VP
  Role --> Coordinator
  Role --> Repair
  Role --> PS
```

Legacy `index.jsp` used `logrole` and included one role dashboard JSP. The new codebase normalizes those roles and uses frontend route defaults plus dashboard allow-lists. The largest remaining role-parity gaps are **VP**, **service coordinator**, **repair team**, and **product support**.

---

## 4. Migrated module map

```mermaid
flowchart TB
  Dashboard["Next.js Dashboard Shell"]

  subgraph Master["Master Data"]
    Divisions["Divisions / Products<br/>/dashboard/divisions"]
    Employees["Employees / Engineers<br/>/dashboard/employees"]
    Dealers["Dealers<br/>/dashboard/dealers"]
    Company["Company master<br/>pending / decision needed"]
  end

  subgraph Service["Service Management"]
    Services["Services<br/>/dashboard/services<br/>/api/services"]
    UnderRepair["Under repair<br/>/dashboard/under-repair"]
    PendingFRN["Pending FRN<br/>/dashboard/pending-frn"]
    OBPending["OB pending<br/>/dashboard/ob-pending"]
    SCClosed["SC closed<br/>/dashboard/sc-closed"]
    ClosedProduct["Closed product<br/>/dashboard/new-closed"]
    Scrap["Scrap list<br/>/dashboard/scrap-list"]
    JobSheet["Job sheet<br/>/dashboard/services/[id]/jobsheet"]
  end

  subgraph Activity["Activity and Calls"]
    Calls["Call register<br/>/dashboard/call-list"]
    ClosedCalls["Closed calls<br/>/dashboard/closed-calls"]
    PendingActivity["Pending activity<br/>/dashboard/pending-activity"]
    ClosedActivity["Closed activity<br/>/dashboard/closed-activity"]
  end

  subgraph PRFOB["PRF / OB"]
    PRFOpen["PRF/OB list<br/>/dashboard/prf-ob-admin"]
    PRFClosed["Closed PRF/OB<br/>/dashboard/prf-ob-closed"]
  end

  subgraph Quality["Quality / FQC / BIR"]
    NonSale["Non-salable list<br/>/dashboard/non-salable-admin<br/>/api/nonsaleable/admin/pending"]
    Salables["Salables<br/>/dashboard/salables<br/>/api/nonsaleable/salables"]
    BIR["BIR list<br/>/dashboard/bir-admin<br/>/api/bir/admin/pending"]
    ClosedBIR["Closed BIR<br/>/dashboard/bir-closed-admin<br/>/api/bir/admin/completed"]
  end

  subgraph Spares["Spares"]
    SparesEntry["Spares entry<br/>/dashboard/spares-entry<br/>/api/partnumber-entry"]
    SparesList["Spares list<br/>/dashboard/spares<br/>engineer/coordinator parity review"]
    SparesCompleted["Spares completed<br/>/dashboard/spares-completed<br/>engineer/coordinator parity review"]
  end

  subgraph AdminOps["Reports / Escalations / Settings"]
    Reports["Reports<br/>/dashboard/reports"]
    Escalations["Escalations<br/>/dashboard/escalations"]
    Settings["Settings shell<br/>/dashboard/settings"]
    MailSettings["Email / dropdown / auto mail config<br/>pending / decision needed"]
  end

  Dashboard --> Master
  Dashboard --> Service
  Dashboard --> Activity
  Dashboard --> PRFOB
  Dashboard --> Quality
  Dashboard --> Spares
  Dashboard --> AdminOps
```

---

## 5. Typical request flow

```mermaid
sequenceDiagram
  autonumber
  participant U as User
  participant FE as Next.js Dashboard
  participant Auth as Role/Auth Layer
  participant API as Spring Boot Controller
  participant SVC as Service Layer
  participant DB as PostgreSQL
  participant XLS as Excel Export

  U->>FE: Open dashboard route
  FE->>Auth: Resolve role and allowed path
  Auth-->>FE: Allow route or redirect
  FE->>API: GET /api module endpoint
  API->>SVC: Apply business filters
  SVC->>DB: Query via JPA repository
  DB-->>SVC: Return rows
  SVC-->>API: DTO/page response
  API-->>FE: JSON response
  FE-->>U: Render table/cards/actions

  opt Export
    U->>FE: Click export
    FE->>API: GET export endpoint with filters
    API->>SVC: Normalize filters and date range
    SVC->>DB: Query export rows
    SVC->>XLS: Generate .xlsx
    XLS-->>FE: Download blob
    FE-->>U: Save Excel file
  end
```

---

## 6. Data and migration view

```mermaid
flowchart LR
  LegacyDB[("Legacy MySQL / JSP-era tables")]
  Import["Data import / migration scripts"]
  Flyway["Flyway migrations<br/>schema corrections and additions"]
  Postgres[("PostgreSQL new database")]

  subgraph Tables["Key migrated / aligned tables"]
    ServiceMaster["service_master"]
    Employee["employee"]
    PRFOB["prfobmaster"]
    NonSaleable["nonsaleablemaster"]
    BIRMaster["birmaster"]
    PartEntry["partnumber_entry"]
    SpareMaster["sparepart_master"]
  end

  LegacyDB --> Import
  Import --> Postgres
  Flyway --> Postgres
  Postgres --> ServiceMaster
  Postgres --> Employee
  Postgres --> PRFOB
  Postgres --> NonSaleable
  Postgres --> BIRMaster
  Postgres --> PartEntry
  Postgres --> SpareMaster
```

The migrated modules preserve legacy table intent where possible. Some migrations include compatibility corrections for pre-existing imported tables, including missing IDs, missing columns, and date column type normalization.

---

## 7. Completed vs pending at architecture level

| Area | Architecture status |
|------|---------------------|
| Dashboard shell | Migrated to Next.js App Router |
| Admin operational lists | Mostly migrated |
| Engineer service list | Migrated with role-aware division scope |
| FQC surface | Migrated at navigation/route level |
| BIR / closed BIR | Migrated list/export/delete; edit parity decision pending |
| Non-salable / salables | Migrated list/export/delete; update parity decision pending |
| Spares entry | Migrated |
| Engineer/coordinator spares list | Existing routes, but role/auth parity review pending |
| VP / coordinator / repair / product support | Partial; role parity work pending |
| Company / dropdown / email / auto mail config | Pending or decision needed |
| Auth hardening | Architecture target defined; implementation/audit remains P0 |
| Cutover readiness | Pending QA, data validation, and production readiness checks |

---

## 8. Recommended next diagram updates

Update this document whenever one of these changes:

1. A pending legacy role receives a dedicated dashboard hub.
2. A legacy-only admin configuration module is migrated or retired.
3. Auth hardening is completed and backend APIs enforce role/data scope.
4. Repair-team and coordinator-specific flows are finalized.
5. The production cutover plan is approved.

