-- Legacy birmaster (Batch Inspection Report)
-- Same pattern as V12: backfill columns if the table existed without the full schema.

CREATE TABLE IF NOT EXISTS birmaster (
    id                      SERIAL PRIMARY KEY,
    division                VARCHAR(100),
    entry_date              DATE,
    fqc_in_date             DATE,
    model                   VARCHAR(100),
    configuration           VARCHAR(100),
    received_qty            VARCHAR(100),
    unit_serial_no          VARCHAR(100),
    invoice_no              VARCHAR(100),
    invoice_date            DATE,
    softwr_changes          VARCHAR(100),
    softwr_version          VARCHAR(100),
    hardwr_changes          VARCHAR(100),
    hardware_details        VARCHAR(100),
    accesory_changes        VARCHAR(100),
    accesory_details        VARCHAR(100),
    user_manual_update      VARCHAR(100),
    service_manual_update   VARCHAR(100),
    fqc_remarks             VARCHAR(1000),
    cnr_ref_no              VARCHAR(100),
    ts_team_ver_date        DATE,
    ps_team_ver_date        DATE,
    final_status            VARCHAR(100),
    closed_date             DATE,
    acces_chng_remark       VARCHAR(1000) NOT NULL DEFAULT '',
    hrdwr_chang_remark      VARCHAR(1000) NOT NULL DEFAULT '',
    sftwr_chng_remark       VARCHAR(1000) NOT NULL DEFAULT '',
    cnr_relese_date         DATE,
    bir_ref_no              VARCHAR(100) NOT NULL DEFAULT '',
    supplier                VARCHAR(100) NOT NULL DEFAULT '',
    sc_engg                 VARCHAR(100) NOT NULL DEFAULT '',
    ps_engg                 VARCHAR(100) NOT NULL DEFAULT '',
    approved_date           DATE,
    cnr_tec_ref_num         VARCHAR(100) NOT NULL DEFAULT '',
    unit_in_date            DATE,
    tech_remarks            VARCHAR(1000),
    "current_date"          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS division VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS entry_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS fqc_in_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS model VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS configuration VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS received_qty VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS unit_serial_no VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS invoice_no VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS invoice_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS softwr_changes VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS softwr_version VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS hardwr_changes VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS hardware_details VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS accesory_changes VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS accesory_details VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS user_manual_update VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS service_manual_update VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS fqc_remarks VARCHAR(1000);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS cnr_ref_no VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS ts_team_ver_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS ps_team_ver_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS final_status VARCHAR(100);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS closed_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS acces_chng_remark VARCHAR(1000) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS hrdwr_chang_remark VARCHAR(1000) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS sftwr_chng_remark VARCHAR(1000) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS cnr_relese_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS bir_ref_no VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS supplier VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS sc_engg VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS ps_engg VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS approved_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS cnr_tec_ref_num VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS unit_in_date DATE;
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS tech_remarks VARCHAR(1000);
ALTER TABLE birmaster ADD COLUMN IF NOT EXISTS "current_date" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_birmaster_final_status ON birmaster (final_status);
CREATE INDEX IF NOT EXISTS idx_birmaster_fqc_in_date ON birmaster (fqc_in_date);
CREATE INDEX IF NOT EXISTS idx_birmaster_division ON birmaster (division);
