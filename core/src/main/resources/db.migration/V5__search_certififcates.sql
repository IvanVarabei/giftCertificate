select id, name, description, price, duration, create_date, last_update_date
from gift_certificate where true
------------------------------------------
                        and id in (
        SELECT gift_certificate_id
        FROM certificate_tag
                 LEFT JOIN tag ON tag_id = tag.id
        WHERE tag.name IN ('cheap', 'gym')
        GROUP BY gift_certificate_id
        HAVING COUNT(tag_id) = 2)
------------------------------------------
                        and name ilike '%e%'
------------------------------------------
                        and description ilike '%fr%'
------------------------------------------
--order by name
------------------------------------------
order by last_update_date
------------------------------------------
    desc