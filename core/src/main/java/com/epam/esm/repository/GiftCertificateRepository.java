package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT gc.id   as gc_id,\n" +
            "       gc.name as gc_name,\n" +
            "       description,\n" +
            "       price,\n" +
            "       duration,\n" +
            "       creat_date,\n" +
            "       last_update_date,\n" +
            "       t.id    as t_id,\n" +
            "       t.name  as t_name\n" +
            "FROM gift_certificate gc\n" +
            "         left join gift_certificate_tag gct\n" +
            "                   on gc.id = gct.gift_certificate_id\n" +
            "         left join tag t on gct.tag_id = t.id";

    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(con -> con.prepareStatement(FIND_ALL_GIFT_CERTIFICATES,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (rs, rowNum) -> {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong("gc_id"));
            giftCertificate.setName(rs.getString("gc_name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(BigDecimal.valueOf(rs.getInt("price")));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreatedDate(rs.getTimestamp("creat_date").toLocalDateTime());
            giftCertificate.setUpdatedDate(rs.getTimestamp("last_update_date").toLocalDateTime());
            giftCertificate.setTags(extractTags(rs));
            return giftCertificate;
        });
    }

    private List<Tag> extractTags(ResultSet rs) throws SQLException {
        List<Tag> tags = new ArrayList<>();
        long currentGiftCertificateId = rs.getLong("gc_id");
        do {
            if (rs.getLong("t_id") != 0) {
                Tag tag = new Tag();
                tag.setId(rs.getLong("t_id"));
                tag.setName(rs.getString("t_name"));
                tags.add(tag);
            }
        } while (rs.next() && rs.getLong("gc_id") == currentGiftCertificateId);
        if (!rs.isAfterLast()) {
            rs.previous();
        }
        return tags;
    }
}
