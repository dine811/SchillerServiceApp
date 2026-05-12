-- sparepart_master (existing JPA SparePart entity had no Flyway table)
CREATE TABLE IF NOT EXISTS sparepart_master (
    spare_id         SERIAL PRIMARY KEY,
    part_number      VARCHAR(1000),
    comp_models      VARCHAR(1000),
    def_mod_brd_name VARCHAR(300),
    def_type         VARCHAR(300),
    division         VARCHAR(100)
);

ALTER TABLE sparepart_master ADD COLUMN IF NOT EXISTS part_number VARCHAR(1000);
ALTER TABLE sparepart_master ADD COLUMN IF NOT EXISTS comp_models VARCHAR(1000);
ALTER TABLE sparepart_master ADD COLUMN IF NOT EXISTS def_mod_brd_name VARCHAR(300);
ALTER TABLE sparepart_master ADD COLUMN IF NOT EXISTS def_type VARCHAR(300);
ALTER TABLE sparepart_master ADD COLUMN IF NOT EXISTS division VARCHAR(100);
