--todo create 2 certficates
--todo create 2 tags
--todo create relations
INSERT INTO public.gift_certificate(id, name, description, price, duration, create_date, last_update_date)
VALUES ('1', 'certificate 1', 'description 1', 100, 5, now()::timestamp, now()::timestamp);