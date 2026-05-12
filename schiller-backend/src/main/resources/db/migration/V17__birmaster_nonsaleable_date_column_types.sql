-- Legacy / partial imports may store dates as VARCHAR. Hibernate maps LocalDate → DATE and LocalDateTime → TIMESTAMP.
-- Align PostgreSQL types with entities Birmaster and Nonsaleablemaster.

-- birmaster: DATE columns
DO $$
DECLARE
  col text;
  cols text[] := ARRAY[
    'entry_date',
    'fqc_in_date',
    'invoice_date',
    'ts_team_ver_date',
    'ps_team_ver_date',
    'closed_date',
    'cnr_relese_date',
    'approved_date',
    'unit_in_date'
  ];
BEGIN
  FOREACH col IN ARRAY cols
  LOOP
    IF EXISTS (
      SELECT 1
      FROM information_schema.columns c
      WHERE c.table_schema = current_schema()
        AND c.table_name = 'birmaster'
        AND c.column_name = col
        AND c.data_type IN ('character varying', 'character', 'text')
    ) THEN
      EXECUTE format(
        'ALTER TABLE birmaster ALTER COLUMN %I TYPE DATE USING (NULLIF(trim(%I::text), '''')::date)',
        col,
        col
      );
    END IF;
  END LOOP;
END $$;

-- birmaster: current_date → TIMESTAMP (LocalDateTime)
DO $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM information_schema.columns c
    WHERE c.table_schema = current_schema()
      AND c.table_name = 'birmaster'
      AND c.column_name = 'current_date'
      AND c.data_type IN ('character varying', 'character', 'text')
  ) THEN
    ALTER TABLE birmaster
      ALTER COLUMN "current_date" TYPE TIMESTAMP WITHOUT TIME ZONE
      USING (NULLIF(trim("current_date"::text), '')::timestamp);
  END IF;
END $$;

-- nonsaleablemaster: DATE columns
DO $$
DECLARE
  col text;
  cols text[] := ARRAY[
    'entry_date',
    'fqc_in_date',
    'replace_ship_date',
    'defect_unit_recived_date',
    'sc_inward_date',
    'tent_date_ship_date',
    'ship_date_fqc'
  ];
BEGIN
  FOREACH col IN ARRAY cols
  LOOP
    IF EXISTS (
      SELECT 1
      FROM information_schema.columns c
      WHERE c.table_schema = current_schema()
        AND c.table_name = 'nonsaleablemaster'
        AND c.column_name = col
        AND c.data_type IN ('character varying', 'character', 'text')
    ) THEN
      EXECUTE format(
        'ALTER TABLE nonsaleablemaster ALTER COLUMN %I TYPE DATE USING (NULLIF(trim(%I::text), '''')::date)',
        col,
        col
      );
    END IF;
  END LOOP;
END $$;

-- nonsaleablemaster: current_date → TIMESTAMP
DO $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM information_schema.columns c
    WHERE c.table_schema = current_schema()
      AND c.table_name = 'nonsaleablemaster'
      AND c.column_name = 'current_date'
      AND c.data_type IN ('character varying', 'character', 'text')
  ) THEN
    ALTER TABLE nonsaleablemaster
      ALTER COLUMN "current_date" TYPE TIMESTAMP WITHOUT TIME ZONE
      USING (NULLIF(trim("current_date"::text), '')::timestamp);
  END IF;
END $$;
