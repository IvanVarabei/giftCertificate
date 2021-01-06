package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class GiftCertificateRepository {
    private static final String FIND_ALL_GIFT_CERTIFICATES = "SELECT id, name, description, price, duration, " +
            "creat_date, last_update_date FROM gift_certificate";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATES, (rs, rowNum) -> {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setGiftCertificateId(rs.getLong("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription("description");
            giftCertificate.setPrice(BigDecimal.valueOf(rs.getInt("price")));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreateDate(rs.getTimestamp("creat_date").toLocalDateTime());
            giftCertificate.setLastUpdateDate(rs.getTimestamp("creat_date").toLocalDateTime());
            return giftCertificate;
        });
    }
}
