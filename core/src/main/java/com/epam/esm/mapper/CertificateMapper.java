package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getBigDecimal("price"));
        giftCertificate.setDuration(rs.getInt("duration"));
        Instant instant = rs.getTimestamp("create_date").toInstant();
        ZonedDateTime zd = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        LocalDateTime ld = zd.toLocalDateTime();
        giftCertificate.setCreatedDate(ZonedDateTime
                .ofInstant(rs.getTimestamp("create_date").toInstant(), ZoneId.of("UTC"))
                .toLocalDateTime());
        giftCertificate.setUpdatedDate(ZonedDateTime
                .ofInstant(rs.getTimestamp("last_update_date").toInstant(), ZoneId.of("UTC"))
                .toLocalDateTime());

        //        giftCertificate.setCreatedDate(rs.getTimestamp("create_date").toLocalDateTime());
//        giftCertificate.setUpdatedDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        giftCertificate.setTags(new ArrayList<>());
        return giftCertificate;
    }
}
