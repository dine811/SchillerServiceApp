-- ============================================================
-- V1: Initial Schema
-- Schiller India Services - PostgreSQL
-- ============================================================

-- Employees / Users
CREATE TABLE IF NOT EXISTS employee (
    emp_id     BIGSERIAL PRIMARY KEY,
    emp_name   VARCHAR(100) NOT NULL,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(20) NOT NULL DEFAULT 'engineer',  -- 'admin' or 'engineer'
    division   VARCHAR(100),
    email      VARCHAR(100),
    mobile     VARCHAR(20),
    active     BOOLEAN DEFAULT TRUE
);

-- Region
CREATE TABLE IF NOT EXISTS region (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE
);

-- Branch
CREATE TABLE IF NOT EXISTS branch (
    id          BIGSERIAL PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    region_id   BIGINT REFERENCES region(id)
);

-- Dealer
CREATE TABLE IF NOT EXISTS dealer (
    dealer_id   BIGSERIAL PRIMARY KEY,
    dealer_name VARCHAR(200) NOT NULL,
    region_id   BIGINT REFERENCES region(id)
);

-- Supplier
CREATE TABLE IF NOT EXISTS supplier (
    id            BIGSERIAL PRIMARY KEY,
    supplier_name VARCHAR(200) NOT NULL
);

-- Model
CREATE TABLE IF NOT EXISTS model (
    model_id    BIGSERIAL PRIMARY KEY,
    model_name  VARCHAR(200) NOT NULL,
    sup_name    VARCHAR(200)
);

-- Dropdown Master (for STK/CUST, Unit Status, Def Type, etc.)
CREATE TABLE IF NOT EXISTS dropdown_master (
    id       BIGSERIAL PRIMARY KEY,
    dd_group INTEGER NOT NULL,   -- 1=STK/CUST, 2=UnitStatus, 3=DefType, 4=TypeAcc, 5=WorkType, 6=RepType
    ddvalue  VARCHAR(100) NOT NULL
);

-- Service Master (main form)
CREATE TABLE IF NOT EXISTS service_master (
    id                         BIGSERIAL PRIMARY KEY,
    sc_ref_no                  VARCHAR(50) NOT NULL,
    entry_date                 DATE,
    division                   VARCHAR(100),
    sc_engineer_id             BIGINT REFERENCES employee(emp_id),
    ra_engineer_id             BIGINT REFERENCES employee(emp_id),
    frn_no                     VARCHAR(50),
    frn_date                   DATE,
    ser_comm_inward_date       DATE,
    ser_centre_received_date   DATE,
    stk_cust                   VARCHAR(50),
    region                     VARCHAR(100),
    branch                     VARCHAR(100),
    dealer_name                VARCHAR(200),
    cust_name                  VARCHAR(200),
    supplier_name              VARCHAR(200),
    product_model              VARCHAR(100),
    model_config               VARCHAR(1000),
    unit_sl_no                 VARCHAR(100),
    unit_status                VARCHAR(50),
    dod                        DATE,
    mod_brd_name               VARCHAR(200),
    def_mod_brd_name           VARCHAR(200),
    def_type                   VARCHAR(50),
    type_of_acc                VARCHAR(50),
    def_part_sn                VARCHAR(100),
    def_gir_no                 VARCHAR(100),
    rep_type                   VARCHAR(50),
    rep_gir_no                 VARCHAR(100),
    def_unit_gir_no            VARCHAR(100),
    field_remarks              VARCHAR(500),
    technical_remarks          VARCHAR(500),
    components_used_for_repair VARCHAR(500),
    final_remarks              VARCHAR(500),
    comrcl_dtl_inv_rmrk        VARCHAR(500),
    type_of_work               VARCHAR(50),
    ship_dt_frm_ser_cntr       DATE,
    repaired_brd_stk_date      DATE,
    ship_date_from_commercial  DATE,
    dc_no                      VARCHAR(100),
    disp_adv_no                VARCHAR(100),
    repaired_brd_adv_no        VARCHAR(100),
    part_number                VARCHAR(100),
    compatible_models          VARCHAR(500),
    destination                VARCHAR(200),
    cost                       NUMERIC(10,2),
    report_type                VARCHAR(50),
    created_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for common queries
CREATE INDEX IF NOT EXISTS idx_service_region      ON service_master(region);
CREATE INDEX IF NOT EXISTS idx_service_type_work   ON service_master(type_of_work);
CREATE INDEX IF NOT EXISTS idx_service_report_type ON service_master(report_type);
CREATE INDEX IF NOT EXISTS idx_service_sc_engineer ON service_master(sc_engineer_id);
