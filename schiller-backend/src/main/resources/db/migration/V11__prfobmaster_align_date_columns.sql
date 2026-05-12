-- Legacy / imported `prfobmaster` may store dates as VARCHAR; Hibernate maps them as LocalDate (DATE).
-- Convert to DATE when the column is still character-based.

DO $$
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema = current_schema()
      AND table_name = 'prfobmaster'
      AND column_name = 'entry_date'
      AND data_type IN ('character varying', 'text', 'character')
  ) THEN
    ALTER TABLE prfobmaster
      ALTER COLUMN entry_date TYPE DATE
      USING (
        CASE
          WHEN entry_date IS NULL OR trim(entry_date::text) = '' THEN NULL
          WHEN trim(entry_date::text) ~ '^\d{4}-\d{2}-\d{2}' THEN trim(entry_date::text)::date
          WHEN trim(entry_date::text) ~ '^\d{2}-\d{2}-\d{4}' THEN to_date(trim(entry_date::text), 'DD-MM-YYYY')
          ELSE NULL::date
        END
      );
  END IF;

  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema = current_schema()
      AND table_name = 'prfobmaster'
      AND column_name = 'raised_date'
      AND data_type IN ('character varying', 'text', 'character')
  ) THEN
    ALTER TABLE prfobmaster
      ALTER COLUMN raised_date TYPE DATE
      USING (
        CASE
          WHEN raised_date IS NULL OR trim(raised_date::text) = '' THEN NULL
          WHEN trim(raised_date::text) ~ '^\d{4}-\d{2}-\d{2}' THEN trim(raised_date::text)::date
          WHEN trim(raised_date::text) ~ '^\d{2}-\d{2}-\d{4}' THEN to_date(trim(raised_date::text), 'DD-MM-YYYY')
          ELSE NULL::date
        END
      );
  END IF;

  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema = current_schema()
      AND table_name = 'prfobmaster'
      AND column_name = 'received_date'
      AND data_type IN ('character varying', 'text', 'character')
  ) THEN
    ALTER TABLE prfobmaster
      ALTER COLUMN received_date TYPE DATE
      USING (
        CASE
          WHEN received_date IS NULL OR trim(received_date::text) = '' THEN NULL
          WHEN trim(received_date::text) ~ '^\d{4}-\d{2}-\d{2}' THEN trim(received_date::text)::date
          WHEN trim(received_date::text) ~ '^\d{2}-\d{2}-\d{4}' THEN to_date(trim(received_date::text), 'DD-MM-YYYY')
          ELSE NULL::date
        END
      );
  END IF;

  IF EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_schema = current_schema()
      AND table_name = 'prfobmaster'
      AND column_name = 'executed_date'
      AND data_type IN ('character varying', 'text', 'character')
  ) THEN
    ALTER TABLE prfobmaster
      ALTER COLUMN executed_date TYPE DATE
      USING (
        CASE
          WHEN executed_date IS NULL OR trim(executed_date::text) = '' THEN NULL
          WHEN trim(executed_date::text) ~ '^\d{4}-\d{2}-\d{2}' THEN trim(executed_date::text)::date
          WHEN trim(executed_date::text) ~ '^\d{2}-\d{2}-\d{4}' THEN to_date(trim(executed_date::text), 'DD-MM-YYYY')
          ELSE NULL::date
        END
      );
  END IF;
END $$;
