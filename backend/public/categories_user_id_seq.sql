create sequence categories_user_id_seq;

alter sequence categories_user_id_seq owner to "user";

alter sequence categories_user_id_seq owned by categories.user_id;

