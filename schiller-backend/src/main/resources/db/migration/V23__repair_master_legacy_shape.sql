-- Align repair_master with legacy MySQL dump (repair_master.csv from mysqldump).
-- Previous DBs may have had a wide service_master-like shape; replace for import parity.

DROP TABLE IF EXISTS repair_master CASCADE;

CREATE TABLE repair_master (
    id                  SERIAL PRIMARY KEY,
    entry_date          DATE,
    division            VARCHAR(100),
    sc_ref_no           VARCHAR(100),
    def_gir_no          VARCHAR(100),
    category            VARCHAR(100),
    model               VARCHAR(1000),
    def_brd_mod_name    VARCHAR(1000),
    unit_status         VARCHAR(300),
    tech_remarks        VARCHAR(300),
    comp_used_to_repair VARCHAR(1000),
    finished_date       DATE,
    repaired_by         VARCHAR(300),
    final_remarks       VARCHAR(100),
    status              VARCHAR(100),
    no_of_days          INTEGER,
    ser_id              INTEGER,
    "current_date"      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_repair_master_ser_id ON repair_master(ser_id);
CREATE INDEX IF NOT EXISTS idx_repair_master_sc_ref_no ON repair_master(sc_ref_no);
