# Migration status (brief)

**Branch:** `codex/migrate-repair-team`  
**Baseline:** `origin/main` at `93cb539`  
**Last updated:** October 14, 2026

## Headline

| Metric | Count |
|---|---:|
| Legacy roles | **7** |
| Unique legacy list/workflow screens (estimated) | **~55** |
| New-stack list/hub routes with `page.tsx` | **34** |
| Fully migrated roles | **5** — Admin operations, Engineer, FQC, Service Coordinator, Repair Team |
| Partially migrated roles | **2** — Vice Chancellor, Product Support |
| Estimated pages still pending | **~14–18** |

## Repair Team migration

The Repair Team module is complete on this branch. Its implementation and legacy parity contract are documented in [migration/repair-team/DESIGN.md](./migration/repair-team/DESIGN.md).

| Legacy capability | Modern route/API | Status |
|---|---|---|
| PFRN pending repairs | `/dashboard/repair`, `GET /api/repairs?category=PFRN&status=pending` | Done |
| OB pending repairs | `/dashboard/repair-ob`, `GET /api/repairs?category=OB&status=pending` | Done |
| UR pending repairs | `/dashboard/repair-ur`, `GET /api/repairs?category=UR&status=pending` | Done |
| Completed repairs | `/dashboard/repair-closed`, `GET /api/repairs?status=completed` | Done |
| Repair update/completion | `/dashboard/repair/[id]/edit`, `PUT /api/repairs/{id}` | Done |
| Initiate from eligible service | `POST /api/repairs/from-service/{serviceId}` | Done |
| Filtered Excel export | `GET /api/repairs/export` | Done (`.xlsx`) |
| Repair Team navigation and landing | `/dashboard/repair` | Done |
| Call Register and Closed Calls | Existing routes retained | Done |

The backend synchronizes repair state atomically with `service_master`: initiation sets `RP`; pending updates retain `RP`; completion sets `RC`, copies components, and assigns `report_type=repairlist`. A partial unique index prevents duplicate active repairs for a service.

## Verification evidence

- Backend Maven Docker suite: **18 passed, 0 failed**, including repository-level filter, search, date-bound, pagination, and sorting coverage.
- Frontend changed-file ESLint: **passed with zero warnings/errors**.
- Frontend TypeScript check: **passed**.
- Frontend production build: **passed**, including all five repair routes.
- Full-project lint baseline: **20 errors and 30 warnings**, all in pre-existing unrelated files.
- Docker Compose rebuild: **passed**.
- Flyway: **24 migrations validated; V24 applied successfully**.
- PostgreSQL: **healthy**; Spring Boot/Flyway/Hibernate startup: **passed**.
- Isolated live smoke test: all four queues, role guards, initiation, pending update, completion, immutable completion, service synchronization, and `.xlsx` export: **passed**.
- Final read-only legacy parity audit: **passed with no P0/P1/P2 findings**.
- Synthetic smoke-test data was removed after verification.

Known verification limitation: the frontend has no component-test harness. Repair UI behavior was therefore checked with changed-file lint, TypeScript, production build, and live API/UI smoke testing.

## Remaining priority work

1. Complete Vice Chancellor route/menu parity.
2. Complete Product Support route/menu parity.
3. Migrate Company master and decide the target for legacy configuration/settings screens.
4. Continue cross-cutting authentication, password hashing, and data-scope hardening.
5. Complete role regression and production cutover checks.
