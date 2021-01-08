package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl extends GeneralRepository implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT gc.id   as gc_id,\n" +
            "       gc.name as gc_name,\n" +
            "       description,\n" +
            "       price,\n" +
            "       duration,\n" +
            "       create_date,\n" +
            "       last_update_date,\n" +
            "       t.id    as t_id,\n" +
            "       t.name  as t_name\n" +
            "FROM gift_certificate gc\n" +
            "         left join certificate_tag ct\n" +
            "                   on gc.id = ct.gift_certificate_id\n" +
            "         left join tag t on ct.tag_id = t.id";

    @Override
    public boolean create(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(con -> con.prepareStatement(FIND_ALL_GIFT_CERTIFICATES,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (rs, rowNum) -> {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong("gc_id"));
            giftCertificate.setName(rs.getString("gc_name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getBigDecimal("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreatedDate(rs.getTimestamp("create_date").toLocalDateTime());
            giftCertificate.setUpdatedDate(rs.getTimestamp("last_update_date").toLocalDateTime());
            giftCertificate.setTags(extractTags(rs));
//            long tagId = rs.getLong("t_id");
//            if (tagId != 0) {
//                Tag tag = new Tag();
//                tag.setId(rs.getLong("t_id"));
//                tag.setName(rs.getString("t_name"));
//            }
            return giftCertificate; // todo subquery bindingTags()
        });
    }

    @Override
    public Optional<GiftCertificate> findById(Long certificateId) {
        return Optional.empty();
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public boolean delete(long giftCertificateId) {
        return false;
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
