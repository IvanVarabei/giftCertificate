package com.epam.esm.repository.impl;

import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dto.search.SortOrder.DESC;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CertificateMapper certificateMapper;

    private static final String SQL_CREATE_CERTIFICATE =
            "insert into gift_certificate (name, description, price, duration) values (?, ?, ?, ?);";

    private static final String SQL_READ_CERTIFICATES_BASE =
            "select id, name, description, price, duration, create_date, last_update_date from gift_certificate " +
                    "where true ";

    private static final String SQL_READ_CERTIFICATES_TAGS1 = "and id in (SELECT gift_certificate_id FROM " +
            "certificate_tag LEFT JOIN tag ON tag_id = tag.id WHERE tag.name IN (";

    private static final String SQL_READ_CERTIFICATES_TAGS2 = ") " +
            "GROUP BY gift_certificate_id HAVING COUNT(tag_id) = ?)";

    private static final String SQL_NAME_FILTER = "and name ilike '%";

    private static final String SQL_DESCRIPTION_FILTER = "and description ilike '%";

    private static final String SQL_SORT_FIELD = "order by ";

    private static final String SQL_READ_CERTIFICATE_BY_ID = "select id, name, description, price, duration, " +
            "create_date, last_update_date from gift_certificate where id = ?";

    private static final String SQL_UPDATE_CERTIFICATE = "update gift_certificate set name = ?, description = ?, " +
            "price = ?, duration = ?, last_update_date = ? where id = ?";

    private static final String SQL_DELETE_CERTIFICATE = "delete from gift_certificate where id = ?";

    private static final String BLANK = " ";


    @Override
    @Transactional
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

    /**
     * select id, name, description, price, duration, create_date, last_update_date
     * from gift_certificate
     * where true
     * and id in (
     * SELECT gift_certificate_id
     * FROM certificate_tag
     * LEFT JOIN tag ON tag_id = tag.id
     * WHERE tag.name IN ('cheap', 'gym')
     * GROUP BY gift_certificate_id
     * HAVING COUNT(tag_id) = 2)
     * and name ilike '%e%'
     * and description ilike '%fr%'
     * order by last_update_date
     * desc
     */
    @Override
    public List<GiftCertificate> findAll(SearchCertificateDto searchDto) {
        StringBuilder sb = new StringBuilder(SQL_READ_CERTIFICATES_BASE);
        List<String> tags = searchDto.getTagNames();
        List queryParams = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            sb.append(SQL_READ_CERTIFICATES_TAGS1)
                    .append("?, ".repeat(tags.size() - 1))
                    .append("?")
                    .append(SQL_READ_CERTIFICATES_TAGS2);
            queryParams.addAll(tags);
            queryParams.add(tags.size());
        }
        if (!StringUtils.isBlank(searchDto.getName())) {
            sb.append(SQL_NAME_FILTER)
                    .append(searchDto.getName())
                    .append("%' ");
        }
        if (!StringUtils.isBlank(searchDto.getDescription())) {
            sb.append(SQL_DESCRIPTION_FILTER)
                    .append(searchDto.getDescription())
                    .append("%' ");
        }
        if (searchDto.getSortByField() != null) {
            sb.append(SQL_SORT_FIELD)
                    .append(searchDto.getSortByField())
                    .append(BLANK);
            if (DESC == searchDto.getSortOrder()) {
                sb.append(DESC);
            }
        }
        return jdbcTemplate.query(sb.toString(), certificateMapper, queryParams.toArray());
    }

    @Override
    public Optional<GiftCertificate> findById(Long certificateId) {
        return jdbcTemplate.query(SQL_READ_CERTIFICATE_BY_ID, certificateMapper, certificateId).stream().findAny();
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getUpdatedDate(),
                giftCertificate.getId());
    }

    @Override
    public void delete(Long giftCertificateId) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE, giftCertificateId);
    }
}
