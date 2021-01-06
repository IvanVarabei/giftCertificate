create table gift_certificate
(
    id               serial                   not null
        constraint gift_certificate_pkey
            primary key,
    name             varchar(64)              not null,
    description      varchar(512),
    price            bigint                   not null,
    duration         integer                  not null,
    creat_date       timestamp with time zone not null,
    last_update_date timestamp with time zone
);
--bigserial
create table tag
(
    id   serial      not null
        constraint tag_pkey
            primary key,
    name varchar(64) not null
);

create table gift_certificate_tag
(
    gift_certificate_id integer not null
        constraint fk_gift_certificate
            references gift_certificate,
    tag_id              integer not null
        constraint fk_tag
            references tag,
    constraint gift_certificate_tag_pkey
        primary key (gift_certificate_id, tag_id)
);

