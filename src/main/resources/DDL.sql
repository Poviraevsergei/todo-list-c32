create table if not exists public.users
(
    id         bigserial
        constraint users_pk
            primary key,
    firstname  varchar(100) not null,
    secondname varchar(100) not null,
    created    timestamp(6) not null,
    changed    timestamp(6),
    age        integer
);

alter table public.users
    owner to user32;

create table if not exists public.security
(
    id       bigserial
        constraint security_pk
            primary key,
    login    varchar not null,
    password varchar,
    user_id  bigint  not null
        constraint security_users_id_fk
            references public.users
            on update cascade on delete cascade
);

alter table public.security
    owner to user32;
