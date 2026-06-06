# Schiller India Services — Legacy to Modern Stack Migration Status

**Document type:** Executive / stakeholder overview  
**Scope:** High-level parity between legacy JSP application (`WebContent/`) and the new stack (`schiller-frontend` + `schiller-backend`).  
**Last updated:** April 2026  
**HTML report (print / share):** [MIGRATION_STATUS.html](./MIGRATION_STATUS.html)  
**Architecture diagram:** [ARCHITECTURE_DIAGRAM.md](./ARCHITECTURE_DIAGRAM.md) / [HTML](./ARCHITECTURE_DIAGRAM.html)  
**Remaining migration todo list:** [MIGRATION_REMAINING_TODO.md](./MIGRATION_REMAINING_TODO.md) / [HTML](./MIGRATION_REMAINING_TODO.html)

---

## 1. Executive summary

The organization is migrating from a **Java EE / JSP monolith** (session-based `logrole`, dashboard includes, servlet-backed screens) to a **Next.js front end** and **Spring Boot REST API** with JWT-based roles, Flyway-managed schema, and shared business data where modules have been wired through.

**What is in good shape:** The **operational spine** used by administrators and engineers—service master and queues, call and activity registers, PRF/OB, non-salable and salables, BIR (including closed views and exports), parts/spares entry, escalations, and substantial master data (divisions, employees, dealers)—has been implemented in the new stack with role-aware routing and navigation.

**What still needs deliberate work:** Full **menu and permission parity** for every legacy role (especially **Vice Chancellor**, **service coordinator**, **repair team**, and **product support**), legacy-only **administration** screens (company master, dropdown values, email settings, auto-escalation mail configuration), **repair-team-specific** queues, **batch/mail** jobs and utilities that never lived in the JSP menus, and a **systematic audit** of business rules vs legacy DAO SQL.

Percentages below are **qualitative engineering estimates** of *user-visible menu intent*, not a line-count of the entire WAR. They are useful for planning and communication, not for contractual completion metrics.

---

## 2. Architecture snapshot

| Layer | Legacy | New |
|--------|--------|-----|
| UI | JSP + includes (`index.jsp` → role dashboard) | Next.js App Router under `schiller-frontend/src/app/(dashboard)/` |
| Auth | HTTP session, `logrole` | JWT; role claims aligned to `AppRole` in `schiller-frontend/src/lib/auth.ts` |
| API | Servlets / JSP actions | REST under `schiller-backend` (`/api/...`), Spring Security |
| Data | Legacy JDBC / DAOs | JPA + Flyway migrations where migrated modules apply |

**Related internal documents**

- `docs/AUTHENTICATION_AUTHORIZATION_IMPLEMENTATION_PLAN.md` — auth model and rollout considerations  
- `docs/ADMIN_LEGACY_MODULES.md` — admin legacy module analysis  
- `docs/ENGINEER_SERVICE_MODULE_MIGRATION.md` — engineer / service migration notes  

---

## 3. Legacy entry routing (`index.jsp`)

After login, the legacy shell includes **one** dashboard by session role:

| Legacy `logrole` (session) | Included dashboard | Typical user |
|----------------------------|--------------------|--------------|
| `admin` | `admindashboard.jsp` | Full administration |
| `Engineer` | `engineerdashboard.jsp` | Field / service engineer |
| `FQC` | `FQCdashboard.jsp` | Factory quality control |
| `ViceChancellor` | `VPDashboard.jsp` | Executive / VP-style access |
| `servicecoordinator` | `ServiceCoorDashBoard.jsp` | Coordinator workflows |
| `repairteam` | `repairDashboard.jsp` | Repair bench / workshop |
| *(default)* | `PSdashboard.jsp` | Product support style |

The new app maps these concepts to normalized roles (e.g. `VICE_CHANCELLOR`, `SERVICE_COORDINATOR`, `REPAIR_TEAM`, `PRODUCT_SUPPORT`) and uses **default landing routes** plus **`isAllowedDashboardPath`** to enforce which URLs each role may open.

---

## 4. Completion overview (qualitative)

| Area | Status | Notes |
|------|--------|--------|
| Admin — services & queues | **Strong** | Service master, under repair, pending FRN, OB pending, SC closed, closed product, scrap-style lists where implemented |
| Admin — activity & registers | **Strong** | Pending/closed activity, call register, closed calls, PRF/OB admin + closed, non-salable, salables, BIR admin + closed |
| Admin — spares & escalations | **Good** | Part/spares entry, escalation-related UI where built |
| Admin — master data (partial) | **Mixed** | Divisions, employees, dealers migrated in new app; **company** and some legacy-only masters may still be legacy-only |
| Admin — settings & mail | **Weak / legacy** | Dropdown admin, email settings, auto-escalation mail entry/list (legacy JSPs) not replaced end-to-end in new UI |
| Engineer | **Good** | Core queues and registers reflected in engineer dashboard and APIs; some legacy JSP variants may be consolidated into fewer routes |
| FQC | **Good** | Dedicated FQC landing and paths for activity, non-salable, salables, BIR (+ closed BIR) aligned to legacy FQC menu intent |
| Vice Chancellor | **Good** | `/dashboard/vp` hub, full legacy `VPDashboard.jsp` menu (17 routes), all-divisions API scope; master-data and settings blocked |
| Service coordinator | **Partial** | Legacy coordinator menu includes PRF/OB and **spares list / completed** style flows; new coordinator allow-list is **smaller** than legacy—parity gap |
| Repair team | **Partial** | Legacy repair-specific lists (`repairList*.jsp`); new stack routes repair default to **under repair** but **not** a full repair-menu parity module |
| Product support | **Partial** | Overlap with calls/PRF/BIR; no full PS dashboard clone of legacy `PSdashboard.jsp` |
| Non-UI legacy (jobs, exports, DAO-only) | **Not counted** | Mail jobs, batch DAOs, and servlet-only flows remain a large **separate** migration bucket |

### Order-of-magnitude estimates (menu / product intent)

| Segment | Approx. range | Disclaimer |
|---------|----------------|--------------|
| Admin day-to-day operational menus | **~70–85%** | Excludes company/settings/email/escalation-mail admin unless separately delivered |
| Engineer surface | **~65–80%** | Depends on consolidation of legacy-only JSP variants |
| FQC (small legacy menu) | **~80–90%** | Field-level and rule parity still need verification per screen |
| VP (legacy `VPDashboard.jsp`) | **~85–90%** | Hub + nav + APIs wired; PRF/OB/BIR/non-salable **update** APIs still absent (list/export/delete OK) |
| Coordinator / repair / PS | **~25–50%** each | Intentionally scoped smaller in new `auth.ts` vs broad legacy sidebars |
| Entire legacy WAR (all JSP + jobs) | **Minority by file count** | Most legacy files are **not** yet superseded; delivery focus has been the **main operational spine** |

---

## 5. Notable gaps and risks (planning)

1. **Role allow-list vs navigation** — Any link shown in `navigation.ts` that is **not** allowed in `auth.ts` will block users mid-flow; menus and `isAllowedDashboardPath` should be reviewed together (example class: engineer **spares** routes if present in nav but absent from allowed prefixes).
2. **VP update APIs** — PRF/OB, non-salable, and BIR list screens lack `PUT` endpoints; VP can list/export/delete but not full legacy edit parity on those modules.
3. **Service coordinator spares** — Legacy `spareslist_*` coordinator flows need explicit new routes + API parity or documented retirement.
4. **Repair team** — Dedicated repair list / closed repair experiences from `repairDashboard.jsp` need either dedicated Next pages + APIs or documented merge into existing service queues.
5. **Admin configuration** — Company master, `DropdownValuesForm.jsp`, `EmailSettings.jsp`, and auto-escalation mail JSPs remain **high-visibility** gaps for “we can turn off legacy” discussions.
6. **Parity testing** — Filters, division scope, engineer vs admin exports, and edge cases in legacy SQL vs new `Specification`/service layer require **structured regression**, not assumption from UI similarity.

---

## 6. Recommended next steps

1. **Menu matrix** — Spreadsheet: each legacy sidebar link (per role) → URL → status (Done / Partial / N/A / Won’t migrate).  
2. **Close role gaps** — Prioritize coordinator spares, repair lists, VP scope decision, PS landing parity.  
3. **Settings / mail** — Decide migrate vs retire vs externalize (ticketing, MDM, etc.).  
4. **Hardening** — Role smoke tests against `defaultDashboardRouteForRole` and `isAllowedDashboardPath`, plus API contract tests for critical lists and exports.

---

## 7. Document control

| Version | Date | Summary |
|---------|------|---------|
| 1.0 | Apr 2026 | Initial stakeholder migration overview |

*Prepared from legacy `WebContent/index.jsp` role routing, legacy dashboard JSP menus, and current `schiller-frontend` / `schiller-backend` structure. For detailed module breakdowns, use the linked `docs/*` files above.*
