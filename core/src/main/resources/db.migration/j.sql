create table certificate_tag
(
    gift_certificate_id integer not null
        constraint certificate_tag_gift_certificate_id_fkey
            references gift_certificate
            on delete cascade,
    tag_id              integer not null
        constraint certificate_tag_tag_id_fkey
            references tag
            on delete cascade,
    constraint certificate_tag_pkey
        primary key (gift_certificate_id, tag_id)
);