INSERT INTO public.gift_certificate(id, name, description, price, duration)
VALUES (1, 'goldie''s gym', '5 free visits', 9.99, 7),
       (2, 'Kfc birthday', '50% off', 5.55, 16);


INSERT INTO public.tag(id, name)
VALUES (1, 'gym'),
       (2, 'cheap');


INSERT INTO public.certificate_tag(gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2);

