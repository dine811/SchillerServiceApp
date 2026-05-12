-- Legacy MySQL nonsaleablemaster → PostgreSQL (non-salable register + salables = same table, final_status Pending vs Completed)
-- If the table already existed without the full column set, CREATE TABLE IF NOT EXISTS is a no-op; ADD COLUMN IF NOT EXISTS backfills.

CREATE TABLE IF NOT EXISTS nonsaleablemaster (
    id              SERIAL PRIMARY KEY,
    division        VARCHAR(60),
    entry_date      DATE,
    unit_details    VARCHAR(50),
    fqc_in_date     DATE,
    region          VARCHAR(50),
    branch          VARCHAR(50),
    engineer        VARCHAR(50),
    dealer          VARCHAR(50),
    supplier        VARCHAR(50),
    model           VARCHAR(50),
    model_s_n       VARCHAR(50),
    unit_config     VARCHAR(50),
    cust_name       VARCHAR(50),
    reported_problm VARCHAR(250),
    replaced_unit_s_n VARCHAR(250),
    replace_ship_date DATE,
    defect_unit_recived_date DATE,
    fqc_observation VARCHAR(250),
    sc_inward_date  DATE,
    sc_engg         VARCHAR(50),
    sc_observation  VARCHAR(250),
    required_parts  VARCHAR(250),
    root_cause      VARCHAR(250),
    action_plan     VARCHAR(600),
    tent_date_ship_date DATE,
    ship_date_fqc   DATE,
    fqc_final_remark VARCHAR(550),
    final_status    VARCHAR(600),
    "current_date"  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS division VARCHAR(60);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS entry_date DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS unit_details VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS fqc_in_date DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS region VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS branch VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS engineer VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS dealer VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS supplier VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS model VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS model_s_n VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS unit_config VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS cust_name VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS reported_problm VARCHAR(250);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS replaced_unit_s_n VARCHAR(250);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS replace_ship_date DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS defect_unit_recived_date DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS fqc_observation VARCHAR(250);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS sc_inward_date DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS sc_engg VARCHAR(50);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS sc_observation VARCHAR(250);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS required_parts VARCHAR(250);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS root_cause VARCHAR(250);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS action_plan VARCHAR(600);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS tent_date_ship_date DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS ship_date_fqc DATE;
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS fqc_final_remark VARCHAR(550);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS final_status VARCHAR(600);
ALTER TABLE nonsaleablemaster ADD COLUMN IF NOT EXISTS "current_date" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_nonsaleable_final_status ON nonsaleablemaster (final_status);
CREATE INDEX IF NOT EXISTS idx_nonsaleable_entry_date ON nonsaleablemaster (entry_date);
CREATE INDEX IF NOT EXISTS idx_nonsaleable_division ON nonsaleablemaster (division);
