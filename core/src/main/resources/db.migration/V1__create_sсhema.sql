create table gift_certificate
(
<<<<<<< HEAD
    id               serial                   not null primary key,
    name             varchar(64)              not null,
    description      varchar(512),
    price            numeric(16, 2)           not null,
    duration         integer                  not null,
    creat_date       timestamp with time zone not null,
    last_update_date timestamp with time zone
);

create table tag
(
    id   serial      not null primary key,
    name varchar(64) not null
||||||| 4fca925
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
=======
    id          bigserial                not null,
    name        text                     not null unique,
    description text,
    price       bigint                   not null,
    duration    integer                  not null,
    created_at  timestamp with time zone not null,
    updated_at  timestamp with time zone not null,
    CONSTRAINT gift_certificate_pkey primary key (id),
    CONSTRAINT positive_price CHECK (price >= 0)
>>>>>>> feature/multy
);

<<<<<<< HEAD
create table gift_certificate_tag
(
    gift_certificate_id integer not null references gift_certificate,
    tag_id              integer not null references tag,
    constraint gift_certificate_tag_pkey
        primary key (gift_certificate_id, tag_id)
);
||||||| 4fca925
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

=======
--todo tag  and certificate_tag


>>>>>>> feature/multy
