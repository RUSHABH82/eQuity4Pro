CREATE TYPE category_enum AS ENUM ('S40', 'S40NEXT', 'S250', 'OTHERS');


create table if not exists public.company_details
(
    symbol           varchar(255) not null primary key,
    name             varchar(255),
    last_price       double precision,
    market_cap_in_cr double precision,
    industry         varchar(255),
    sector           varchar(255),
    category         varchar(255) NOT NULL DEFAULT 'OTHERS'
);

create table if not exists public.history_data
(
    id                     bigserial primary key,
    timestamp              bigint,
    open                   double precision,
    close                  double precision,
    high                   double precision,
    low                    double precision,
    volume                 bigint,
    company_details_symbol varchar(255) not null
        constraint fk_company_detail references public.company_details ON DELETE CASCADE,
    constraint uk_timestamp_company unique (company_details_symbol, timestamp)
);





