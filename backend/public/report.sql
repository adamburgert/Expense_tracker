create table report
(
    id           bigserial,
    created_at   timestamp,
    description  varchar,
    total_amount double precision,
    file_path    varchar,
    report_type  varchar,
    updated_at   timestamp
);

alter table report
    owner to "user";

