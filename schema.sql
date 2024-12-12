-- we don't know how to generate root <with-no-name> (class Root) :(

comment on database postgres is 'default administrative connection database';

create sequence categories_user_id_seq;

alter sequence categories_user_id_seq owner to "user";

create sequence integer;

alter sequence integer owner to "user";

create sequence categories_id_seq;

alter sequence categories_id_seq owner to "user";

create sequence roles_id_seq;

alter sequence roles_id_seq owner to "user";

create sequence expenses_seq;

alter sequence expenses_seq owner to "user";

create sequence report_seq;

alter sequence report_seq owner to "user";

create sequence roles_seq;

alter sequence roles_seq owner to "user";

create table categories
(
    id          bigint,
    name        varchar,
    description varchar,
    date        timestamp,
    updated_at  timestamp,
    user_id     bigint default nextval('categories_user_id_seq'::regclass),
    count       integer,
    price       double precision,
    category_id bigint default nextval('"integer"'::regclass),
    total_price double precision,
    created_at  timestamp
);

alter table categories
    owner to "user";

alter sequence categories_user_id_seq owned by categories.user_id;

alter sequence integer owned by categories.category_id;

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

create table roles
(
    id        bigint,
    role_name varchar,
    name      varchar,
    admin     varchar,
    "USER"    varchar,
    user_id   bigint,
    role_id   bigint
);

alter table roles
    owner to "user";

create table users
(
    id         bigserial,
    created_at timestamp,
    updated_at timestamp,
    email      varchar,
    password   varchar,
    username   varchar,
    role       varchar
);

alter table users
    owner to "user";

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

