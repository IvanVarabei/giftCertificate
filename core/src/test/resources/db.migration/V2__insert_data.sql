INSERT INTO public.gift_certificate(id, name, description, price, duration)
VALUES (1, 'goldie''s gym', '5 free visits', 9.99, 7),
       (2, 'Kfc birthday', '50% off', 5.55, 16),
       (3, 'Silver screen', 'one film', 4.99, 9);


INSERT INTO public.tag(id, name)
VALUES (1, 'gym'),
       (2, 'cheap'),
       (3, 'rest');


INSERT INTO public.certificate_tag(gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (3, 3),
       (3, 2);

