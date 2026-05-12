-- Align legacy/imported `callregister_demo` with JPA mapping when the table already existed
-- before V4: `CREATE TABLE IF NOT EXISTS` skipped creation, so Hibernate validate can fail with
-- "missing column [id]" (or missing `call` — MySQL legacy dumps omit the `call` column).
--
-- Tables WITH EXISTING DATA: This migration does not DELETE or TRUNCATE rows.
--   - ADD COLUMN id BIGSERIAL PRIMARY KEY: PostgreSQL assigns a new surrogate id to each
--     existing row (1, 2, 3, …); all other columns (division, engineer, remarks, etc.) stay
--     unchanged.
--   - ADD COLUMN call: new column is nullable; existing rows get NULL for `call` unless you
--     backfill later.

DO $$
DECLARE
  r RECORD;
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.tables
    WHERE table_schema = current_schema() AND table_name = 'callregister_demo'
  ) THEN
    RETURN;
  END IF;

  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema = current_schema()
      AND table_name = 'callregister_demo'
      AND column_name = 'id'
  ) THEN
    FOR r IN
      SELECT tc.constraint_name
      FROM information_schema.table_constraints tc
      WHERE tc.table_schema = current_schema()
        AND tc.table_name = 'callregister_demo'
        AND tc.constraint_type = 'PRIMARY KEY'
    LOOP
      EXECUTE format('ALTER TABLE callregister_demo DROP CONSTRAINT %I', r.constraint_name);
    END LOOP;

    ALTER TABLE callregister_demo ADD COLUMN id BIGSERIAL PRIMARY KEY;
  END IF;

  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema = current_schema()
      AND table_name = 'callregister_demo'
      AND column_name = 'call'
  ) THEN
    ALTER TABLE callregister_demo ADD COLUMN call VARCHAR(100);
  END IF;
END $$;
