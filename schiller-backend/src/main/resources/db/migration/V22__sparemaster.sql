CREATE TABLE IF NOT EXISTS sparemaster (
    id BIGSERIAL PRIMARY KEY,
    division VARCHAR(100),
    sc_engg VARCHAR(100),
    entry_date VARCHAR(100),
    supplier VARCHAR(100),
    model VARCHAR(150),
    partnumber VARCHAR(150),
    def_mod_brd_name VARCHAR(200),
    reason VARCHAR(300),
    reference VARCHAR(300),
    gir_no VARCHAR(100),
    issued_by VARCHAR(150),
    returnable_status VARCHAR(120),
    remarks VARCHAR(2000),
    returned_date VARCHAR(120),
    final_status VARCHAR(120),
    req_date_time TIMESTAMP,
    issued_date_time TIMESTAMP,
    returned_date_time TIMESTAMP,
    completed_date_time TIMESTAMP,
    qty VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_sparemaster_status ON sparemaster (final_status);
CREATE INDEX IF NOT EXISTS idx_sparemaster_division ON sparemaster (division);
