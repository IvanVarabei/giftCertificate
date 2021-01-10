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