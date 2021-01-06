create table gift_certificate
(
    id          bigserial                not null,
    name        text                     not null unique,
    description text,
    price       bigint                   not null,
    duration    integer                  not null,
    created_at  timestamp with time zone not null,
    updated_at  timestamp with time zone not null,
    CONSTRAINT gift_certificate_pkey primary key (id),
    CONSTRAINT positive_price CHECK (price >= 0)
);

--todo tag  and certificate_tag


