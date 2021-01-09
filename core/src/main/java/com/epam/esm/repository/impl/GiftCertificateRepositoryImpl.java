package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRepository tagRepository;
    private final CertificateMapper certificateMapper;
    private static final String SQL_READ_CERTIFICATES =
            "select id, name, description, price, duration, create_date, last_update_date from gift_certificate";
    private static final String SQL_READ_CERTIFICATE_BY_ID = "select id, name, description, price, duration, " +
            "create_date, last_update_date from gift_certificate where id = ?";
    private static final String SQL_DELETE_CERTIFICATE = "delete from gift_certificate where id = ?";

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_READ_CERTIFICATES, certificateMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(Long certificateId) {
        return jdbcTemplate.query(SQL_READ_CERTIFICATE_BY_ID, certificateMapper, certificateId).stream().findAny();
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public boolean delete(long giftCertificateId) {
        return 1 == jdbcTemplate.update(SQL_DELETE_CERTIFICATE, giftCertificateId);
    }
}
