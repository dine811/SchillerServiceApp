-- PRF/OB master — legacy MySQL table `prfobmaster` (PRFOB_AdminList.jsp, Export_PRFOBDao.PrfObAdmin).
--
-- If `prfobmaster` already exists (partial / older schema), CREATE TABLE IF NOT EXISTS is a no-op
-- and indexes on missing columns would fail. We ADD COLUMN IF NOT EXISTS for every column, then index.

CREATE TABLE IF NOT EXISTS prfobmaster (
    id                 SERIAL PRIMARY KEY,
    division           VARCHAR(100),
    entry_date         DATE,
    sc_engg            VARCHAR(100),
    work_type          VARCHAR(100),
    raised_date        DATE,
    received_date      DATE,
    region             VARCHAR(100),
    branch             VARCHAR(100),
    engineer           VARCHAR(100),
    dealer             VARCHAR(100),
    model              VARCHAR(100),
    supplier           VARCHAR(100),
    warrenty_status    VARCHAR(100),
    prfob_ref_no       VARCHAR(100),
    crm_ref_no         VARCHAR(100),
    remarks            VARCHAR(600),
    status_type        VARCHAR(100),
    executed_date      DATE,
    part_type          VARCHAR(100) NOT NULL DEFAULT '',
    part_description   VARCHAR(100) NOT NULL DEFAULT '',
    currentdate_time   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    rec_dt_frm_sc      VARCHAR(255)
);

-- Reconcile pre-existing table (empty or different shape) so indexes below succeed.
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS division VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS entry_date DATE;
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS sc_engg VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS work_type VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS raised_date DATE;
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS received_date DATE;
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS region VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS branch VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS engineer VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS dealer VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS model VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS supplier VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS warrenty_status VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS prfob_ref_no VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS crm_ref_no VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS remarks VARCHAR(600);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS status_type VARCHAR(100);
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS executed_date DATE;
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS part_type VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS part_description VARCHAR(100) NOT NULL DEFAULT '';
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS currentdate_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE prfobmaster ADD COLUMN IF NOT EXISTS rec_dt_frm_sc VARCHAR(255);

CREATE INDEX IF NOT EXISTS idx_prfobmaster_status ON prfobmaster (status_type);
CREATE INDEX IF NOT EXISTS idx_prfobmaster_division ON prfobmaster (division);
CREATE INDEX IF NOT EXISTS idx_prfobmaster_entry_date ON prfobmaster (entry_date);
