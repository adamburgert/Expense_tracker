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

