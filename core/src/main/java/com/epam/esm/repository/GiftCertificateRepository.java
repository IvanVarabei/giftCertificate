package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(Long certificateId);

    void update(GiftCertificate giftCertificate);

    void delete(Long giftCertificateId);
}
