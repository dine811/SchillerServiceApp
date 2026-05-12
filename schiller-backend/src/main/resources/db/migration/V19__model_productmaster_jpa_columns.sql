-- JPA Model / Productmaster entities expect productmaster and model.model_desc + model.product_id.
-- V1 only created model(model_id, model_name, sup_name), which breaks GET /api/models (missing columns / join target).

CREATE TABLE IF NOT EXISTS productmaster (
    product_id     SERIAL PRIMARY KEY,
    division       VARCHAR(100),
    division_name  VARCHAR(200)
);

ALTER TABLE model ADD COLUMN IF NOT EXISTS model_desc VARCHAR(200);
ALTER TABLE model ADD COLUMN IF NOT EXISTS product_id INTEGER;
