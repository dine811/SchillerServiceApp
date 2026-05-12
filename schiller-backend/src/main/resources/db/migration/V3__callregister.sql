-- Call register (legacy `callregister`) — used by CallRegister entity / admin call list API.
-- Flyway runs before Hibernate schema validation (ddl-auto: validate).

CREATE TABLE IF NOT EXISTS callregister (
    id                      BIGSERIAL PRIMARY KEY,
    division                VARCHAR(100),
    sc_engg                 VARCHAR(100),
    call_date               VARCHAR(100),
    call_type               VARCHAR(100),
    call                    VARCHAR(100),
    region                  VARCHAR(100),
    branch                  VARCHAR(100),
    dealer                  VARCHAR(100),
    engineer                VARCHAR(100),
    model                   VARCHAR(100),
    type_of_call            VARCHAR(100),
    type_of_communication   VARCHAR(100),
    remarks                 VARCHAR(2000),
    duration                VARCHAR(100),
    status_type             VARCHAR(100),
    entry_date              VARCHAR(100)
);
