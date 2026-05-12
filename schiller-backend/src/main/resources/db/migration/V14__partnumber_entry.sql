-- Legacy PartEntry servlet → partnumber_entry (distinct from sparepart_master catalogue)
CREATE TABLE IF NOT EXISTS partnumber_entry (
    id                 SERIAL PRIMARY KEY,
    part_number        VARCHAR(1000) NOT NULL,
    brd_name           VARCHAR(300) NOT NULL,
    compatible_models  VARCHAR(1000) NOT NULL,
    cost               NUMERIC(12, 2) NOT NULL,
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE partnumber_entry ADD COLUMN IF NOT EXISTS part_number VARCHAR(1000);
ALTER TABLE partnumber_entry ADD COLUMN IF NOT EXISTS brd_name VARCHAR(300);
ALTER TABLE partnumber_entry ADD COLUMN IF NOT EXISTS compatible_models VARCHAR(1000);
ALTER TABLE partnumber_entry ADD COLUMN IF NOT EXISTS cost NUMERIC(12, 2);
ALTER TABLE partnumber_entry ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
