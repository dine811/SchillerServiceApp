-- V17 used information_schema with table_schema = current_schema().
-- If search_path lists a user schema before public, current_schema() is not 'public' while
-- birmaster / nonsaleablemaster live in public — the DO block never matched and varchar columns stayed.
-- This migration finds the actual schema from pg_catalog and converts remaining text date columns.

DO $$
DECLARE
  bir_schema text;
  ns_schema text;
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
  SELECT n.nspname INTO bir_schema
  FROM pg_class c
  JOIN pg_namespace n ON n.oid = c.relnamespace
  WHERE c.relname = 'birmaster' AND c.relkind = 'r'
  ORDER BY (n.nspname = 'public') DESC
  LIMIT 1;

  IF bir_schema IS NOT NULL THEN
    FOREACH col IN ARRAY cols
    LOOP
      IF EXISTS (
        SELECT 1
        FROM information_schema.columns c
        WHERE c.table_schema = bir_schema
          AND c.table_name = 'birmaster'
          AND c.column_name = col
          AND c.data_type IN ('character varying', 'character', 'text')
      ) THEN
        EXECUTE format(
          'ALTER TABLE %I.%I ALTER COLUMN %I TYPE DATE USING (
            CASE
              WHEN %I IS NULL OR trim(%I::text) = '''' THEN NULL
              WHEN trim(%I::text) ~ ''^\d{4}-\d{2}-\d{2}'' THEN trim(%I::text)::date
              WHEN trim(%I::text) ~ ''^\d{2}-\d{2}-\d{4}'' THEN to_date(trim(%I::text), ''DD-MM-YYYY'')
              ELSE NULL::date
            END
          )',
          bir_schema, 'birmaster', col, col, col, col, col, col, col
        );
      END IF;
    END LOOP;

    IF EXISTS (
      SELECT 1
      FROM information_schema.columns c
      WHERE c.table_schema = bir_schema
        AND c.table_name = 'birmaster'
        AND c.column_name = 'current_date'
        AND c.data_type IN ('character varying', 'character', 'text')
    ) THEN
      EXECUTE format(
        'ALTER TABLE %I.%I ALTER COLUMN %I TYPE TIMESTAMP WITHOUT TIME ZONE USING (
          NULLIF(trim(%I::text), '''')::timestamp
        )',
        bir_schema, 'birmaster', 'current_date', 'current_date'
      );
    END IF;
  END IF;

  SELECT n.nspname INTO ns_schema
  FROM pg_class c
  JOIN pg_namespace n ON n.oid = c.relnamespace
  WHERE c.relname = 'nonsaleablemaster' AND c.relkind = 'r'
  ORDER BY (n.nspname = 'public') DESC
  LIMIT 1;

  IF ns_schema IS NOT NULL THEN
    cols := ARRAY[
      'entry_date',
      'fqc_in_date',
      'replace_ship_date',
      'defect_unit_recived_date',
      'sc_inward_date',
      'tent_date_ship_date',
      'ship_date_fqc'
    ];
    FOREACH col IN ARRAY cols
    LOOP
      IF EXISTS (
        SELECT 1
        FROM information_schema.columns c
        WHERE c.table_schema = ns_schema
          AND c.table_name = 'nonsaleablemaster'
          AND c.column_name = col
          AND c.data_type IN ('character varying', 'character', 'text')
      ) THEN
        EXECUTE format(
          'ALTER TABLE %I.%I ALTER COLUMN %I TYPE DATE USING (
            CASE
              WHEN %I IS NULL OR trim(%I::text) = '''' THEN NULL
              WHEN trim(%I::text) ~ ''^\d{4}-\d{2}-\d{2}'' THEN trim(%I::text)::date
              WHEN trim(%I::text) ~ ''^\d{2}-\d{2}-\d{4}'' THEN to_date(trim(%I::text), ''DD-MM-YYYY'')
              ELSE NULL::date
            END
          )',
          ns_schema, 'nonsaleablemaster', col, col, col, col, col, col, col
        );
      END IF;
    END LOOP;

    IF EXISTS (
      SELECT 1
      FROM information_schema.columns c
      WHERE c.table_schema = ns_schema
        AND c.table_name = 'nonsaleablemaster'
        AND c.column_name = 'current_date'
        AND c.data_type IN ('character varying', 'character', 'text')
    ) THEN
      EXECUTE format(
        'ALTER TABLE %I.%I ALTER COLUMN %I TYPE TIMESTAMP WITHOUT TIME ZONE USING (
          NULLIF(trim(%I::text), '''')::timestamp
        )',
        ns_schema, 'nonsaleablemaster', 'current_date', 'current_date'
      );
    END IF;
  END IF;
END $$;
