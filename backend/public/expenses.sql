create table expenses
(
    price       double precision,
    id          bigserial,
    amount      integer,
    date        timestamp,
    description varchar,
    updated_at  timestamp,
    created_at  timestamp,
    category_id bigint,
    report_id   bigint,
    user_id     bigint,
    total_price double precision,
    min_price   double precision,
    max_price   double precision,
    minamount   double precision,
    maxamount   double precision
);

alter table expenses
    owner to "user";

