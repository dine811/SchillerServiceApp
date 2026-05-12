-- Partially imported or older `callregister` / `callregister_demo` tables may omit columns
-- that JPA maps (e.g. `duration`). Adds only missing columns — does not DROP or recreate tables.

DO $$
DECLARE
  tbl text;
  r RECORD;
BEGIN
  FOR tbl IN SELECT unnest(ARRAY['callregister_demo', 'callregister'])
  LOOP
    IF NOT EXISTS (
      SELECT 1 FROM information_schema.tables
      WHERE table_schema = current_schema() AND table_name = tbl
    ) THEN
      CONTINUE;
    END IF;

    FOR r IN
      SELECT * FROM (VALUES
        ('division', 'VARCHAR(100)'),
        ('sc_engg', 'VARCHAR(100)'),
        ('call_date', 'VARCHAR(100)'),
        ('call_type', 'VARCHAR(100)'),
        ('region', 'VARCHAR(100)'),
        ('branch', 'VARCHAR(100)'),
        ('dealer', 'VARCHAR(100)'),
        ('engineer', 'VARCHAR(100)'),
        ('model', 'VARCHAR(100)'),
        ('type_of_call', 'VARCHAR(100)'),
        ('type_of_communication', 'VARCHAR(100)'),
        ('remarks', 'VARCHAR(2000)'),
        ('duration', 'VARCHAR(100)'),
        ('status_type', 'VARCHAR(100)'),
        ('entry_date', 'VARCHAR(100)')
      ) AS t(col_name, col_type)
    LOOP
      IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns c
        WHERE c.table_schema = current_schema()
          AND c.table_name = tbl
          AND c.column_name = r.col_name
      ) THEN
        EXECUTE format('ALTER TABLE %I ADD COLUMN %I %s', tbl, r.col_name, r.col_type);
      END IF;
    END LOOP;
  END LOOP;
END $$;
