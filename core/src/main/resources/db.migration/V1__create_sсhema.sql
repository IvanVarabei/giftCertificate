create table gift_certificate
(
    id               serial                   not null primary key,
    name             varchar(64)              not null,
    description      varchar(512),
    price            numeric(16, 2)           not null,
    duration         integer                  not null,
    create_date      timestamptz not null default (now() at time zone 'utc'),
    last_update_date timestamptz not null default (now() at time zone 'utc'),
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
    gift_certificate_id integer not null,
    tag_id              integer not null references tag,
    constraint gift_certificate_id_fkey
        foreign key (gift_certificate_id)
            references gift_certificate (id)
            on delete cascade,
    constraint tag_id_fkey
        foreign key (tag_id)
            references tag (id)
            on delete cascade,
    constraint gift_certificate_tag_pkey
        primary key (gift_certificate_id, tag_id)
);

