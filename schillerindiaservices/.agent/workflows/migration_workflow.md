---
description: How to execute the Stack Migration Phase 1 (Backend & Frontend Scaffold)
---
# Schiller Migration Workflow

This workflow will guide us through the first major phase of the migration starting tomorrow: creating the first fully working module (Service Management) on the new stack (Spring Boot + Next.js).

## Step 1: Database Baseline (PostgreSQL)
// turbo
1. Ensure the PostgreSQL schema for the first module (e.g., `service_master`) is defined in `schiller-backend/src/main/resources/db/migration/V1__initial_schema.sql`.
2. Connect to the `schillerdb` and apply the migrations using Flyway on backend startup. 

## Step 2: Spring Boot Backend Scaffold (Service Module)
1. Generate the JPA Entity class for the module (`ServiceMaster.java`).
2. Generate the Spring Data Repository (`ServiceRepository.java`).
3. Generate the Business Service layer (`ServiceMasterService.java`).
4. Generate the REST API Controller (`ServiceController.java`) mapping the endpoints to `/api/services`.
5. Map any DTOs needed (`ServiceDto.java`) to ensure the request shapes match the frontend expectations.
6. Make sure JWT authentication routes are set up and bypass rules are applied for dev if needed.

## Step 3: API Verification
// turbo
1. Start the backend app via `docker-compose up -d backend postgres`.
2. Test the API endpoints via `curl` or Swagger at `http://localhost:8090/swagger-ui.html`. Ensure we can `GET /api/services` and `POST /api/services`.

## Step 4: Next.js Frontend Scaffold
1. Instantiate the Next.js project inside `schiller-frontend`. (If not already created).
2. Install necessary UI components like shadcn/ui and configure Tailwind.
3. Build the `/services` Data Table list page fetching from `http://localhost:8090/api/services`.
4. Build the `/services/add` form to POST new service entries.

## Step 5: End-to-End Test
// turbo-all
1. Bring up the whole stack `docker-compose up -d`.
2. View the `/services` page in the browser at `http://localhost:3000`.
3. Submit a new service record and ensure it persists in the PostgreSQL database.
