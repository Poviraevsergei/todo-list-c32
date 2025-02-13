create table public.security
(
    id       bigserial
        constraint security_pk
            primary key,
    login    varchar not null,
    password varchar,
    user_id  bigint
        constraint security_users_id_fk
            references public.users
            on update cascade on delete cascade
);