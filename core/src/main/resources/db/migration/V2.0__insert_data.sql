INSERT INTO gift_certificate(name, description, price, duration)
VALUES ('goldie''s gym', '5 free visits', 9.99, 7),
       ('Kfc birthday', '50% off', 5.55, 16),
       ('Silver screen', 'one film', 4.99, 9);

INSERT INTO tag(name)
VALUES ('gym'),
       ('cheap'),
       ('rest');

INSERT INTO public.certificate_tag(gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (3, 3),
       (3, 2);

