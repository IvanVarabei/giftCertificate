package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRepository tagRepository;
    private final CertificateMapper certificateMapper;
    private static final String SQL_CREATE_CERTIFICATE =
            "insert into gift_certificate (name, description, price, duration) values (?, ?, ?, ?);";
    private static final String SQL_READ_CERTIFICATES =
            "select id, name, description, price, duration, create_date, last_update_date from gift_certificate";
    private static final String SQL_READ_CERTIFICATE_BY_ID = "select id, name, description, price, duration, " +
            "create_date, last_update_date from gift_certificate where id = ?";
    private static final String SQL_UPDATE_CERTIFICATE =
            "update gift_certificate set name = ?, description = ?, price = ?, duration = ? where id = ?";
    private static final String SQL_DELETE_CERTIFICATE = "delete from gift_certificate where id = ?";

    @Override// update
    @Transactional // test
    public GiftCertificate save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            preparedStatement.setString(index++, giftCertificate.getName());
            preparedStatement.setString(index++, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(index++, giftCertificate.getPrice());
            preparedStatement.setInt(index, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);
        giftCertificate.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        giftCertificate.setCreatedDate(((Timestamp) (keyHolder.getKeys().get("create_date"))).toLocalDateTime());
        giftCertificate.setUpdatedDate(((Timestamp) (keyHolder.getKeys().get("last_update_date"))).toLocalDateTime());
        return giftCertificate;
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
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getId());
    }

    @Override
    public void delete(long giftCertificateId) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE, giftCertificateId);
    }
}
