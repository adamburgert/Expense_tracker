create table roles
(
    id         bigint,
    role_name  varchar,
    name       varchar,
    admin      varchar,
    "USER"     varchar,
    user_id    bigint,
    role_id    bigint,
    role_user  varchar,
    role_admin varchar
);

alter table roles
    owner to "user";

