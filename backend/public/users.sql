create table users
(
    id         bigserial,
    created_at timestamp,
    updated_at timestamp,
    email      varchar,
    password   varchar,
    username   varchar,
    role       varchar,
    name       varchar
);

alter table users
    owner to "user";

