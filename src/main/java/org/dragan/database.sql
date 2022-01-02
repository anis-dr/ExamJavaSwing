-- auto-generated definition
create
database exam_java
    with owner postgres;

-- auto-generated definition
create table personnes
(
    id    serial
        constraint personnes_pk
            primary key,
    nom   text    not null,
    genre integer not null
);

alter table personnes
    owner to postgres;
