create table if not exists stock_scan_yield
(
    id                 bigserial not null primary key,
    symbol             varchar(255),
    buy                double precision,
    sell               double precision,
    variation          double precision,
    company_category   varchar(255),
    scanned_time_stamp bigint
);