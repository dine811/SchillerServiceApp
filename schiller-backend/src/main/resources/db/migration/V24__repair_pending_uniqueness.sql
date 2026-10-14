-- One service may have repair history, but never more than one active repair.
CREATE UNIQUE INDEX IF NOT EXISTS uq_repair_master_pending_service
    ON repair_master (ser_id)
    WHERE LOWER(TRIM(status)) = 'pending';

CREATE INDEX IF NOT EXISTS idx_repair_master_queue
    ON repair_master (status, category, entry_date, id);
