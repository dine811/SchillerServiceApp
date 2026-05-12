-- Some PostgreSQL imports or hand-built schemas used wrong names:
--   `status` stored duration strings (e.g. "02 : 10")
--   `closed_date` stored status text (e.g. "Completed")
-- Legacy dump (Dump20260314.sql) and JPA use: `duration`, `status_type`, `entry_date`.
-- This migration aligns names + data without recreating tables.

DO $$
DECLARE
  tbl text;
BEGIN
  FOREACH tbl IN ARRAY ARRAY['callregister_demo', 'callregister']
  LOOP
    IF NOT EXISTS (
      SELECT 1 FROM information_schema.tables
      WHERE table_schema = current_schema() AND table_name = tbl
    ) THEN
      CONTINUE;
    END IF;

    -- `status` column actually holds duration values
    IF EXISTS (
      SELECT 1 FROM information_schema.columns
      WHERE table_schema = current_schema() AND table_name = tbl AND column_name = 'status'
    ) THEN
      IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema() AND table_name = tbl AND column_name = 'duration'
      ) THEN
        EXECUTE format('ALTER TABLE %I RENAME COLUMN status TO duration', tbl);
      ELSE
        EXECUTE format($u$
          UPDATE %I SET duration = status
          WHERE duration IS NULL OR btrim(duration::text) = ''
        $u$, tbl);
        EXECUTE format('ALTER TABLE %I DROP COLUMN status', tbl);
      END IF;
    END IF;

    -- `closed_date` column actually holds status_type values (e.g. Completed)
    IF EXISTS (
      SELECT 1 FROM information_schema.columns
      WHERE table_schema = current_schema() AND table_name = tbl AND column_name = 'closed_date'
    ) THEN
      IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = current_schema() AND table_name = tbl AND column_name = 'status_type'
      ) THEN
        EXECUTE format('ALTER TABLE %I RENAME COLUMN closed_date TO status_type', tbl);
      ELSE
        EXECUTE format($u$
          UPDATE %I SET status_type = closed_date
          WHERE status_type IS NULL OR btrim(status_type::text) = ''
        $u$, tbl);
        EXECUTE format('ALTER TABLE %I DROP COLUMN closed_date', tbl);
      END IF;
    END IF;
  END LOOP;
END $$;
