-- Job sheet (repair log lines per service) — parity with legacy MySQL `jobsheet`
-- Workflow status on service — parity with legacy `service_master.status`

CREATE TABLE IF NOT EXISTS jobsheet (
    id                SERIAL PRIMARY KEY,
    repair_date       VARCHAR(255),
    enginner_name     VARCHAR(255),
    observation       VARCHAR(255),
    repair_activity   VARCHAR(255),
    time_spent        VARCHAR(255),
    remark            VARCHAR(255),
    ser_id            BIGINT NOT NULL REFERENCES service_master(id) ON DELETE CASCADE,
    repair_date1      VARCHAR(255),
    repair_date2      VARCHAR(255),
    repair_date3      VARCHAR(255),
    repair_date4      VARCHAR(255),
    enginner_name1    VARCHAR(255),
    enginner_name2    VARCHAR(255),
    enginner_name3    VARCHAR(255),
    enginner_name4    VARCHAR(255),
    observation1      VARCHAR(255),
    observation2      VARCHAR(255),
    observation3      VARCHAR(255),
    observation4      VARCHAR(255),
    repair_activity1  VARCHAR(255),
    repair_activity2  VARCHAR(255),
    repair_activity3  VARCHAR(255),
    repair_activity4  VARCHAR(255),
    time_spent1       VARCHAR(255),
    time_spent2       VARCHAR(255),
    time_spent3       VARCHAR(255),
    time_spent4       VARCHAR(255),
    remark1           VARCHAR(255),
    remark2           VARCHAR(255),
    remark3           VARCHAR(255),
    remark4           VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_jobsheet_ser_id ON jobsheet(ser_id);

ALTER TABLE service_master ADD COLUMN IF NOT EXISTS status VARCHAR(1000);
