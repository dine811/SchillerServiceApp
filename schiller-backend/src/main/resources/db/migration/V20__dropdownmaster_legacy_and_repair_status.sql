-- Legacy MySQL uses table `dropdownmaster` (dd_id, ddname, ddvalue); JPA was mapped to `dropdown_master` (id, dd_group, ddvalue).
-- Imported DBs / old dumps → 500 on GET /api/dropdowns/group/N. Align storage and backfill.

CREATE TABLE IF NOT EXISTS dropdownmaster (
    dd_id   BIGSERIAL PRIMARY KEY,
    ddname  VARCHAR(255),
    ddvalue VARCHAR(255)
);

-- Copy from Flyway V1 `dropdown_master` into legacy-shaped table when dropdownmaster is still empty
DO $$
BEGIN
    INSERT INTO dropdownmaster (dd_id, ddname, ddvalue)
    SELECT id, CAST(dd_group AS VARCHAR(255)), ddvalue
    FROM dropdown_master
    WHERE NOT EXISTS (SELECT 1 FROM dropdownmaster LIMIT 1);
EXCEPTION
    WHEN undefined_table THEN
        NULL;
END $$;

SELECT setval(
    pg_get_serial_sequence('dropdownmaster', 'dd_id'),
    GREATEST(COALESCE((SELECT MAX(dd_id) FROM dropdownmaster), 1), 1)
);

ALTER TABLE service_master ADD COLUMN IF NOT EXISTS repair_status VARCHAR(100);
