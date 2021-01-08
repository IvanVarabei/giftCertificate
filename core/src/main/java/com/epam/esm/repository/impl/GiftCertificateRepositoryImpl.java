package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRepository tagRepository;
    private static final String SQL_FIND_CERTIFICATES = "select * from gift_certificate";

    @Override
    public boolean save(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATES, (rs, rowNum) -> {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getBigDecimal("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreatedDate(rs.getTimestamp("create_date").toLocalDateTime());
            giftCertificate.setUpdatedDate(rs.getTimestamp("last_update_date").toLocalDateTime());
            giftCertificate.setTags(tagRepository.getTagsByCertificateId(giftCertificate.getId()));
            return giftCertificate;
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
}
