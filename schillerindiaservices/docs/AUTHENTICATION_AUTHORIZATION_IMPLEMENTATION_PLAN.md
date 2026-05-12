# Authentication & Authorization Implementation Plan

This document proposes how to implement authentication and authorization in the new app (`schiller-frontend` + `schiller-backend`) by using the current codebase and legacy behavior as reference.

## 1) Current state analysis

## Backend (Spring Boot)

- `SecurityConfig` is present, but currently allows all requests:
  - `sessionCreationPolicy(STATELESS)`
  - `.anyRequest().permitAll()`
  - TODO comment indicates JWT filter was intended but not implemented.
- There is no `/api/auth/login`, token service, or request filter for user identity.
- Employee user data exists in the `employee` table/entity (`username`, `password`, `role`, `active`), but passwords are currently stored and used as plain text.
- API endpoints are grouped functionally (`/api/services`, `/api/prf-ob`, `/api/bir`, `/api/nonsaleable`, etc.) and currently have no role restrictions.

## Frontend (Next.js app router)

- App immediately redirects `/` to `/dashboard`.
- No login route/page in the new frontend.
- No middleware (`middleware.ts`) to guard routes.
- `Navbar` and `Sidebar` currently show static "Admin User" and expose all navigation links.
- API services do not attach auth headers/tokens.

## Legacy app behavior (JSP)

- Login (`login.jsp`) validates user via `EmployeeDao`, then stores session attributes:
  - `logusername`, `loguserid`, `logname`, `logrole`.
- Role-based landing/dashboard is done in `index.jsp`:
  - `admin`, `Engineer`, `FQC`, `ViceChancellor`, `servicecoordinator`, `repairteam`, else `PSdashboard`.
- Logout invalidates session (`logout.jsp`).

Conclusion: Legacy behavior is session+role driven; new app has no auth yet. The implementation should preserve role-based access patterns while modernizing security.

## 2) Target auth model (recommended)

Use **JWT access token + refresh token** with role claims and centralized policy checks.

- Access token: short-lived (e.g. 15 minutes), signed with server secret.
- Refresh token: longer-lived (e.g. 7 days), stored server-side (hashed) and rotated.
- Transport:
  - Access token in `Authorization: Bearer <token>`.
  - Refresh token in `HttpOnly`, `Secure`, `SameSite=Lax` cookie.
- Backend remains stateless for access validation.
- Frontend stores access token in memory (or secure cookie if you prefer fully cookie-based auth).

Why this model here:
- Matches existing `STATELESS` backend direction.
- Supports role checks cleanly with Spring Security.
- Better than legacy session+plaintext password behavior.

## 3) Role model to carry forward

Normalize legacy role strings to a canonical enum in backend:

- `ADMIN` (legacy: `admin`)
- `ENGINEER` (legacy: `Engineer`)
- `FQC` (legacy: `FQC`)
- `VICE_CHANCELLOR` (legacy: `ViceChancellor`)
- `SERVICE_COORDINATOR` (legacy: `servicecoordinator`)
- `REPAIR_TEAM` (legacy: `repairteam`)
- `PRODUCT_SUPPORT` (legacy fallback `PSdashboard`)

Also enforce `active = true` during login.

## 4) Authorization strategy

Implement both:

1. **Endpoint-level role guards** (`@PreAuthorize` or request matcher rules)
2. **Data-level scope restrictions** (division/owner filtering), especially for engineer flows

### Suggested endpoint policy (first pass)

- Public:
  - `POST /api/auth/login`
  - `POST /api/auth/refresh`
  - `POST /api/auth/logout`
  - `GET /api/health`
- Admin/master management:
  - `/api/employees/**`, `/api/products/**`, `/api/regions/**`, `/api/branches/**`, `/api/dealers/**`
  - Roles: `ADMIN` (optionally `VICE_CHANCELLOR`)
- Operational lists:
  - `/api/services/**`, `/api/prf-ob/**`, `/api/bir/**`, `/api/nonsaleable/**`, `/api/call-register/**`
  - Roles: allow relevant groups, but enforce row-level filtering for non-admin users.

### Data-level policy (critical for engineer pages)

- Admin/FQC/VP: can view all rows.
- Engineer/service coordinator/repair team/product support:
  - restrict by division and/or assigned engineer field where applicable.
- Never trust frontend filter parameters for security; derive scope from authenticated user claims.

## 5) Database and entity changes

Current `employee.password` should become hashed values (BCrypt/Argon2).

Recommended additions:

- `employee` table:
  - `password_hash` (or repurpose `password` to store hash only)
  - `last_login_at` timestamp
  - `failed_login_count` int
  - `locked_until` timestamp (optional)
- `auth_refresh_token` table:
  - `id`, `emp_id`, `token_hash`, `expires_at`, `created_at`, `revoked_at`, `user_agent`, `ip_address`

Use Flyway migrations for all changes.

## 6) Backend implementation blueprint

## 6.1 New components

- `AuthController`
  - `POST /api/auth/login`
  - `POST /api/auth/refresh`
  - `POST /api/auth/logout`
  - `GET /api/auth/me`
- `AuthService`
  - credential verification
  - token issue/rotate/revoke
- `JwtService`
  - generate/validate access token
  - claims: `sub`, `empId`, `role`, `division`, `active`
- `JwtAuthenticationFilter` (`OncePerRequestFilter`)
  - parse bearer token
  - build `Authentication` object
- `CustomUserDetailsService`
  - load employee by username
- `PasswordEncoder` bean
  - BCrypt recommended

## 6.2 Security config changes

- Enable method security (`@EnableMethodSecurity`).
- In `SecurityConfig`:
  - allow only auth + health endpoints publicly
  - require auth for all other endpoints
  - register JWT filter before username/password filter
  - keep CORS with frontend origins

## 6.3 Password migration

Because existing rows may be plain text:

Phase-safe approach:
- One-time migration script to hash known passwords.
- Temporary fallback (short window): if login value matches stored non-hash, hash and update immediately.
- Remove fallback once migration complete.

## 7) Frontend implementation blueprint

## 7.1 Auth UX

- Add `src/app/login/page.tsx`
  - username/password form
  - error states
  - redirect by role after successful login
- Add logout action in navbar/user menu calling `/api/auth/logout`.

## 7.2 Session handling

- Add `AuthProvider` context:
  - current user (`/api/auth/me`)
  - login/logout helpers
- Add route guard:
  - middleware or layout-based guard for `/dashboard/**`
  - redirect unauthenticated users to `/login`

## 7.3 Role-aware navigation

- Replace static user info in `Navbar` and `Sidebar` with authenticated user data.
- Build nav sections from role permissions:
  - Admin sees full modules.
  - Engineer sees engineer pages only.
  - FQC/service-coordinator/repair-team get appropriate subsets.

## 7.4 API layer

- Centralize fetch wrapper to:
  - send bearer token
  - handle 401 by refresh retry once
  - redirect to login on refresh failure

## 8) Legacy parity mapping (initial)

- Legacy `index.jsp` dashboard routing should map to role-specific default routes in new frontend:
  - `ADMIN` -> admin dashboard home
  - `ENGINEER` -> engineer dashboard home
  - `FQC` -> FQC-focused landing
  - `VICE_CHANCELLOR` -> executive view
  - `SERVICE_COORDINATOR` / `REPAIR_TEAM` / `PRODUCT_SUPPORT` -> relevant module hubs

This keeps user experience consistent while moving from server session to token auth.

## 9) Implementation phases

## Phase 1 (foundation)
- Add backend auth endpoints + JWT filter.
- Add login page + basic route protection.
- Protect all APIs (except auth/health).
- Keep existing UI pages functional.

## Phase 2 (authorization hardening)
- Add role-based endpoint restrictions.
- Add data-scope restrictions for engineer and non-admin roles.
- Hide unauthorized nav/page links.

## Phase 3 (security hardening)
- Password migration completion.
- Refresh token rotation + revoke on logout.
- Rate limit login endpoint.
- Audit logging for auth events.

## 10) Testing checklist

- Login success/failure for each role.
- Inactive user cannot login.
- Access token expiration + refresh flow.
- Unauthorized endpoint access returns 401/403 correctly.
- Engineer cannot access admin-only endpoints/pages.
- Division-scoped data cannot be bypassed via query params.
- Logout revokes refresh token and blocks further refresh.

## 11) Recommended first deliverable

Implement Phase 1 in this order:

1. Backend `/api/auth/login|refresh|logout|me` + JWT filter.
2. Frontend `/login` + guarded dashboard layout.
3. Replace static user display in navbar/sidebar with authenticated profile.
4. Lock all APIs behind authentication.

After Phase 1 is stable, proceed role-by-role for engineer/admin authorization rules.
