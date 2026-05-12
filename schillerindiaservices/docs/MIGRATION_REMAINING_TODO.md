# Schiller India Services - Remaining Migration Todo List

**Purpose:** Agent-ready backlog for completing the remaining legacy JSP to Next.js/Spring Boot migration.  
**Source reports reviewed:** `MIGRATION_STATUS.html`, `MIGRATION_STATUS.md`, `ADMIN_LEGACY_MODULES.md`, `ENGINEER_SERVICE_MODULE_MIGRATION.md`, `AUTHENTICATION_AUTHORIZATION_IMPLEMENTATION_PLAN.md`.  
**Last updated:** April 2026

---

## How to use this backlog

- Assign **one agent per ticket** where possible.
- Keep each ticket small enough to finish as one branch / PR.
- The **manager agent** should own this file, update status, resolve overlaps, and review role/auth parity.
- Recommended status values: `TODO`, `IN PROGRESS`, `BLOCKED`, `REVIEW`, `DONE`, `WONT MIGRATE`.

---

## Priority summary

| Priority | Area | Why it matters |
|----------|------|----------------|
| P0 | Role and navigation parity | Users can see links that may be blocked by auth, or may miss legacy links entirely. |
| P0 | Auth and API security hardening | Migration cannot safely replace legacy until APIs and data scopes are enforced. |
| P1 | Coordinator spares | Legacy service coordinator flow is not fully represented in current role access. |
| P1 | Repair team module | Legacy repair dashboard has dedicated repair queues not fully migrated. |
| P1 | VP / Product Support parity | These roles have partial access in the new stack compared with legacy dashboards. |
| P2 | Admin configuration modules | Company, dropdown values, email settings, auto-escalation mail remain legacy-only or unclear. |
| P2 | Edit/update parity for BIR and non-salable | Current migrated admin modules cover list/export/delete/create but not all legacy edit flows. |
| P3 | Regression, exports, docs, cutover | Needed before production switchover. |

---

## Manager agent checklist

| ID | Status | Task | Acceptance criteria |
|----|--------|------|---------------------|
| MGR-001 | TODO | Create a complete legacy menu matrix for all seven dashboards from `index.jsp`. | Every link from `admindashboard.jsp`, `engineerdashboard.jsp`, `FQCdashboard.jsp`, `VPDashboard.jsp`, `ServiceCoorDashBoard.jsp`, `repairDashboard.jsp`, and `PSdashboard.jsp` has a status: Done / Partial / Pending / Wont migrate. |
| MGR-002 | TODO | Map every legacy menu link to a target Next route and backend API. | Matrix includes legacy JSP, target route, API endpoint, role access, data scope, and owner. |
| MGR-003 | TODO | Verify `navigation.ts` and `auth.ts` stay aligned. | No visible navigation item is blocked by `isAllowedDashboardPath`; no allowed route is missing from role navigation unless intentionally hidden. |
| MGR-004 | TODO | Define branch/worktree ownership for parallel agents. | Each agent owns a non-overlapping ticket and branch; shared files such as `auth.ts`, `navigation.ts`, API clients, and schema migrations are merged by manager only. |
| MGR-005 | TODO | Maintain migration decision log. | Any retired legacy JSP flow is documented with reason and stakeholder approval. |

---

## P0 - Role, navigation, and authorization

| ID | Status | Suggested owner | Task | Acceptance criteria |
|----|--------|-----------------|------|---------------------|
| AUTH-001 | TODO | Auth agent | Audit current auth implementation against `AUTHENTICATION_AUTHORIZATION_IMPLEMENTATION_PLAN.md`. | Report says which items are implemented, partial, or pending: login, refresh, logout, `/api/auth/me`, JWT filter, password hashing, route guard, API auth. |
| AUTH-002 | TODO | Auth agent | Enforce backend endpoint role guards. | Operational APIs require authentication; admin/master endpoints are admin-only unless explicitly approved; unauthorized calls return 401/403. |
| AUTH-003 | TODO | Auth agent | Enforce data-level scope for non-admin roles. | Engineer/coordinator/repair/product support cannot bypass division/assignment filters through query params. |
| AUTH-004 | TODO | Frontend role agent | Fix role-specific default landing pages if stakeholders want legacy parity. | `VICE_CHANCELLOR`, `SERVICE_COORDINATOR`, `REPAIR_TEAM`, and `PRODUCT_SUPPORT` land on approved route hubs. |
| AUTH-005 | DONE | Frontend role agent | Align engineer spares routes between nav and auth. | `engineerNavSections` includes `/dashboard/spares` and `/dashboard/spares-completed`; `auth.ts` allows those routes for engineers, and non-admin sidebars are filtered through the same route guard. |
| AUTH-006 | TODO | QA agent | Add role smoke-test checklist. | Manual or automated checklist covers login and route access for ADMIN, ENGINEER, FQC, VICE_CHANCELLOR, SERVICE_COORDINATOR, REPAIR_TEAM, PRODUCT_SUPPORT. |

---

## P1 - Service coordinator remaining work

| ID | Status | Suggested owner | Legacy source | Target | Acceptance criteria |
|----|--------|-----------------|---------------|--------|---------------------|
| SC-001 | TODO | Coordinator agent | `ServiceCoorDashBoard.jsp` | Service coordinator route/nav review | Coordinator navigation matches approved legacy menu scope. |
| SC-002 | TODO | Coordinator agent | Coordinator PRF/OB menu | `/dashboard/prf-ob-admin`, `/dashboard/prf-ob-closed` or coordinator-specific routes | Coordinator can view/update the correct PRF/OB items with correct data scope. |
| SC-003 | TODO | Coordinator spares agent | `spareslist_*` coordinator pages | `/dashboard/spares`, `/dashboard/spares-completed` or dedicated coordinator pages | Coordinator can update spares requests and view completed spares as in legacy. |
| SC-004 | TODO | Backend agent | Legacy spares servlets/DAO | Spares update APIs | APIs support coordinator update workflow, completion status, search/filter/export if legacy had it. |
| SC-005 | TODO | QA agent | Coordinator legacy menu | Regression checklist | Coordinator role smoke test confirms visible links, allowed routes, data scope, and update permissions. |

---

## P1 - Repair team remaining work

| ID | Status | Suggested owner | Legacy source | Target | Acceptance criteria |
|----|--------|-----------------|---------------|--------|---------------------|
| REP-001 | TODO | Repair agent | `repairDashboard.jsp` | Repair module design | Decide whether repair flows are dedicated routes or merged into service queues. Decision recorded. |
| REP-002 | TODO | Repair agent | `repairList*.jsp` | `/dashboard/repair`, `/dashboard/repair-closed`, or approved target routes | Repair team can access pending repair queues equivalent to legacy PFRN/OB/UR flows. |
| REP-003 | TODO | Repair agent | `repairList_Closed.jsp` | Closed repair page or merged closed queue | Closed repair list is available with filters and role scope. |
| REP-004 | TODO | Backend agent | Repair list DAO/servlet logic | Repair APIs or service queue filters | Backend returns the same business statuses as legacy repair queues. |
| REP-005 | TODO | QA agent | Repair dashboard | Regression checklist | Repair role smoke test covers route access, list filters, status transitions, and closed repair visibility. |

---

## P1 - VP and Product Support parity

| ID | Status | Suggested owner | Legacy source | Target | Acceptance criteria |
|----|--------|-----------------|---------------|--------|---------------------|
| VP-001 | TODO | VP agent | `VPDashboard.jsp` | VP role matrix | Exact VP legacy menu is listed and compared against current `VICE_CHANCELLOR` allow-list. |
| VP-002 | TODO | VP agent | VP service/activity menus | `/dashboard/reports` plus approved operational routes | Stakeholders decide whether VP should have broad admin-like read-only access or only reports. |
| VP-003 | TODO | Auth/nav agent | `VPDashboard.jsp`, `auth.ts`, `navigation.ts` | VP nav and path access | VP can open every approved route and cannot access unapproved admin write pages. |
| PS-001 | TODO | Product support agent | `PSdashboard.jsp` | Product support route matrix | Exact PS legacy menu is listed and mapped to new routes or retirement decisions. |
| PS-002 | TODO | Product support agent | PS call/activity/BIR menus | Product support dashboard or approved existing routes | Product support has a clear landing page and correct access to calls, PRF/OB, BIR, and related lists. |
| PS-003 | TODO | QA agent | VP/PS roles | Regression checklist | VP and Product Support smoke tests cover login, default landing, route access, and export/list visibility. |

---

## P2 - Admin configuration and legacy-only admin screens

| ID | Status | Suggested owner | Legacy source | Target | Acceptance criteria |
|----|--------|-----------------|---------------|--------|---------------------|
| ADM-001 | TODO | Admin master agent | Company master JSP/menu | `/dashboard/companies` or approved replacement | Company master is migrated or formally retired. |
| ADM-002 | TODO | Admin config agent | `DropdownValuesForm.jsp` | `/dashboard/settings/dropdowns` or approved replacement | Dropdown values can be managed in new UI or documented as no longer needed. |
| ADM-003 | TODO | Admin mail agent | `EmailSettings.jsp` | `/dashboard/settings/email` or approved replacement | Email settings are migrated, externalized, or documented as environment-only config. |
| ADM-004 | TODO | Escalation agent | `AutoEsclationmail_*`, `Automail_id_list.jsp` | `/dashboard/escalations` enhancement or dedicated config pages | Auto-escalation mail entry/list functions exist in new stack or are explicitly retired. |
| ADM-005 | TODO | Admin nav agent | `admindashboard.jsp` | `dashboardNavSections` | Admin menu clearly separates migrated, pending, and retired modules without dead links. |

---

## P2 - Existing migrated module parity improvements

| ID | Status | Suggested owner | Module | Task | Acceptance criteria |
|----|--------|-----------------|--------|------|---------------------|
| MOD-001 | TODO | BIR agent | BIR | Re-check pending/closed BIR list, delete, export, date filters, division filters, and role scope. | BIR behavior matches legacy for admin/FQC/VP/Product Support where approved. |
| MOD-002 | TODO | BIR agent | BIR | Evaluate whether legacy BIR edit/update flow must be migrated. | Decision recorded; if required, edit page/API implemented and tested. |
| MOD-003 | TODO | Non-salable agent | Non-salable / salables | Re-check pending/completed filters, delete, export, division filter, role scope. | Non-salable and salables behavior matches legacy role expectations. |
| MOD-004 | TODO | Non-salable agent | Non-salable / salables | Evaluate whether legacy non-salable update flow must be migrated. | Decision recorded; if required, edit page/API implemented and tested. |
| MOD-005 | TODO | PRF/OB agent | PRF/OB | Verify admin, engineer, coordinator, VP, and PS access to open/closed PRF/OB. | Each role sees correct records and actions. |
| MOD-006 | TODO | Calls agent | Call register | Verify pending/closed calls parity across admin, engineer, repair, PS roles. | Legacy list filters and role visibility are documented and implemented. |
| MOD-007 | TODO | Activity agent | Pending/closed activity | Verify edit, validation, close/reopen, filters, and role scope. | Activity module passes role-based regression checklist. |
| MOD-008 | TODO | Services agent | Service queues | Verify all queue pages use legacy-equivalent status filters. | Services, under repair, pending FRN, OB pending, SC closed, closed product, scrap list match legacy definitions. |
| MOD-009 | TODO | Export agent | Excel exports | Compare columns and filters for critical exports. | Exports have approved columns, file names, date handling, and role scope. |

---

## P3 - Testing, deployment, and cutover

| ID | Status | Suggested owner | Task | Acceptance criteria |
|----|--------|-----------------|------|---------------------|
| QA-001 | TODO | QA agent | Build a per-role manual test script. | Script covers login, nav, list loading, search, export, create/edit/delete where applicable, logout. |
| QA-002 | TODO | QA agent | Add smoke tests for critical routes. | At minimum: admin dashboard, engineer dashboard, FQC dashboard, BIR, non-salable, services, PRF/OB, calls. |
| QA-003 | TODO | Backend QA agent | Add API contract tests for critical filters. | Tests cover role scope, date range, status filters, and division filters. |
| QA-004 | TODO | Data agent | Validate Flyway migrations against imported legacy data. | Fresh DB and migrated DB both start cleanly; Hibernate validation passes. |
| QA-005 | TODO | Data agent | Prepare legacy-to-new data validation checklist. | Counts between legacy and new match for key tables and statuses. |
| CUT-001 | TODO | Release manager | Define cutover plan. | Plan covers freeze window, data import, DNS/app switch, rollback, and legacy read-only period. |
| CUT-002 | TODO | Release manager | Define retired legacy modules. | Stakeholders sign off on modules marked `WONT MIGRATE`. |
| CUT-003 | TODO | Release manager | Production readiness review. | Secrets, CORS, HTTPS, backup, logs, monitoring, and user onboarding are checked. |

---

## Suggested parallel agent grouping

| Agent | Tickets |
|-------|---------|
| Manager agent | MGR-001 to MGR-005, merge coordination, shared file review |
| Auth/role agent | AUTH-001 to AUTH-006 |
| Coordinator agent | SC-001 to SC-005 |
| Repair agent | REP-001 to REP-005 |
| VP/PS agent | VP-001 to VP-003, PS-001 to PS-003 |
| Admin config agent | ADM-001 to ADM-005 |
| Module parity agents | MOD-001 to MOD-009 split by module |
| QA/release agent | QA-001 to QA-005, CUT-001 to CUT-003 |

---

## First recommended sprint

1. Complete `MGR-001` and `MGR-002` so every remaining page is visible.
2. Complete `AUTH-005` because it is a clear nav/auth mismatch risk.
3. Run `AUTH-001` to confirm the real current auth state vs the older implementation plan.
4. Assign `SC-003` and `REP-002` to separate agents because those are the largest remaining role-specific page gaps.
5. Assign `VP-001` and `PS-001` as read-only discovery tasks before building new pages.

