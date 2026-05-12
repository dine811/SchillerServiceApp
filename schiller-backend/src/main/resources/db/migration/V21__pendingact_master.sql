CREATE TABLE IF NOT EXISTS pendingact_master (
    id BIGSERIAL PRIMARY KEY,
    division VARCHAR(100),
    sc_engg VARCHAR(100),
    entry_date VARCHAR(100),
    initiate_date VARCHAR(100),
    model VARCHAR(200),
    pending_activity VARCHAR(300),
    responsible VARCHAR(300),
    pending_form VARCHAR(200),
    tar_closed_date VARCHAR(100),
    closed_date VARCHAR(100),
    remarks VARCHAR(2000),
    status_type VARCHAR(100),
    sc_incharge_remark VARCHAR(2000)
);

CREATE INDEX IF NOT EXISTS idx_pendingact_master_status_type
    ON pendingact_master (status_type);

CREATE INDEX IF NOT EXISTS idx_pendingact_master_division
    ON pendingact_master (division);
