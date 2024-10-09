--liquibase formatted sql

--changeset vitaliid:init-schema-1-currency
create table if not exists currency
(
    id     uuid        not null
        primary key,
    symbol varchar(20) not null
        constraint uk1s3curjt67vt8s1bl16ahdut6
            unique
);

--changeset vitaliid:init-schema-1-rates
create table if not exists rates_take
(
    id        uuid   not null
        primary key,
    rates     jsonb  not null,
    timestamp bigint not null,
    base_id   uuid   not null
        constraint base_id_currency_id_key
            references currency
);
