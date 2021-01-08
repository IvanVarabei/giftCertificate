--todo create 2 certificates
--todo create 2 tags
--todo create relations
INSERT INTO public.gift_certificate(id, name, description, price, duration, create_date, last_update_date)
VALUES ('1', 'certificate 1', 'description 1', 100, 5, now()::timestamp, now()::timestamp);


INSERT INTO public.tag(id, name)
VALUES (1, 'tag 1');


INSERT INTO public.certificate_tag(gift_certificate_id, tag_id)
VALUES (1, 1);