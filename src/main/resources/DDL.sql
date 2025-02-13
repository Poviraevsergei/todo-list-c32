create table public.users
(
    id          bigserial
        constraint users_pk
            primary key,
    firstname   varchar(100) not null,
    second_name varchar(100) not null,
    created     timestamp(6) not null,
    changed     timestamp(6),
    age         integer
);

alter table public.users
    owner to user32;

create trigger add_user_create_trg
    before insert
    on public.users
    for each row
execute procedure public.add_log();

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

alter table public.security
    owner to user32;

create unique index user_id_uniq
    on public.security (user_id);

create table public.logs
(
    id      bigserial
        constraint logs_pk
            primary key,
    text    varchar(200) not null,
    created timestamp    not null
);

alter table public.logs
    owner to user32;

