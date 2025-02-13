create table public.employee
(
    id     bigserial
        constraint employee_pk
            primary key,
    name   varchar,
    salary integer
);

alter table public.employee
    owner to user32;

create table public.passport
(
    id             bigserial
        constraint passport_pk
            primary key,
    passport_value varchar not null,
    emp_id         bigint  not null
        unique
        constraint passport_employee_id_fk
            references public.employee
            on update cascade on delete cascade
);

alter table public.passport
    owner to user32;

create table public.telephone
(
    id               bigserial
        constraint telephone_pk
            primary key,
    telephone_number varchar not null,
    eml_id           bigint  not null
        constraint telephone_employee_id_fk
            references public.employee
            on update cascade on delete cascade
);

alter table public.telephone
    owner to user32;

create table public.tech
(
    id        bigserial
        primary key,
    tech_name varchar not null
);

alter table public.tech
    owner to postgres;

create table public.l_eml_tech
(
    id      bigserial
        primary key,
    tech_id bigint not null
        constraint tech_id_fk
            references public.tech
            on update cascade on delete cascade,
    emp_id  bigint not null
        constraint emp_id_fk
            references public.employee
            on update cascade on delete cascade
);

alter table public.l_eml_tech
    owner to postgres;

