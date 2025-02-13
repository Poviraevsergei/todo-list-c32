create table public.logs
(
    id      bigserial
    constraint logs_pk
    primary key,
    text    varchar(200) not null,
    created timestamp    not null
);