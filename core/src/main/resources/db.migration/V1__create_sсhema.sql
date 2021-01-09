create table gift_certificate
(
    id               serial                   not null primary key,
    name             varchar(64)              not null,
    description      varchar(512),
    price            numeric(16, 2)           not null,
    duration         integer                  not null,
    create_date      timestamp with time zone not null default current_timestamp,
    last_update_date timestamp with time zone not null default current_timestamp,
    constraint unique_certificate_name unique (name),
    constraint positive_price check (price > (0)::numeric)
);

create table tag
(
    id   serial      not null primary key,
    name varchar(64) not null,
    constraint unique_tag_name unique (name)
);

create table certificate_tag
(
    gift_certificate_id integer not null references gift_certificate,
    tag_id              integer not null references tag,
    constraint gift_certificate_tag_pkey
        primary key (gift_certificate_id, tag_id)
);

