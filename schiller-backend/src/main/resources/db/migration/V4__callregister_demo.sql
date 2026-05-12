-- Legacy `callregister_demo` — ClosedCalls.jsp / ClosedCalls_Controller (completed rows).
-- Same shape as `callregister` (V3).

CREATE TABLE IF NOT EXISTS callregister_demo (
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
