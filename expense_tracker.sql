create database expense_tracker
    with owner "user";

create sequence public.categories_user_id_seq;

alter sequence public.categories_user_id_seq owner to "user";

create sequence public.categories_category_id_seq;

alter sequence public.categories_category_id_seq owner to "user";

create sequence public.categories_id_seq;

alter sequence public.categories_id_seq owner to "user";

create sequence public.roles_id_seq;

alter sequence public.roles_id_seq owner to "user";

create table public.categories
(
    id          bigint,
    name        varchar,
    description varchar,
    date        timestamp,
    updated_at  timestamp,
    user_id     bigint default nextval('categories_user_id_seq'::regclass),
    count       integer,
    price       double precision,
    category_id bigint default nextval('categories_category_id_seq'::regclass),
    total_price double precision,
    created_at  timestamp
);

alter table public.categories
    owner to "user";

alter sequence public.categories_user_id_seq owned by public.categories.user_id;

alter sequence public.categories_category_id_seq owned by public.categories.category_id;

create table public.expenses
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

alter table public.expenses
    owner to "user";

create table public.roles
(
    id        bigint,
    role_name varchar,
    name      varchar,
    admin     varchar,
    "USER"    varchar,
    role_id   bigserial,
    user_id   bigint
);

alter table public.roles
    owner to "user";

create table public.users
(
    id         bigserial,
    created_at timestamp,
    updated_at timestamp,
    email      varchar,
    password   varchar,
    username   varchar
);

alter table public.users
    owner to "user";

create table public.report
(
    id           bigserial,
    created_at   timestamp,
    description  varchar,
    total_amount double precision
);

alter table public.report
    owner to "user";


