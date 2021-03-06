package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    private static final String CREATE_TAG = "insert into tag (name) values (?)";

    private static final String READ_TAG_BY_ID = "select id, name from tag where id = ?";

    private static final String READ_TAG_BY_NAME = "select id, name from tag where name = ?";

    private static final String READ_TAGS = "select id, name from tag";

    private static final String READ_TAGS_BY_CERTIFICATE_ID =
            "SELECT id, name FROM tag JOIN certificate_tag ON tag.id = tag_id WHERE gift_certificate_id = ?";

    private static final String UPDATE_TAG = "update tag set name = ? where id = ?";

    private static final String DELETE_TAG = "delete from tag where id = ?";

    private static final String BIND_TAG =
            "insert into certificate_tag (gift_certificate_id, tag_id) values (?, ?)";

    private static final String UNBIND_TAGS = "delete from certificate_tag where gift_certificate_id = ?";

    @Override
    public Tag save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        tag.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(READ_TAGS, tagMapper);
    }

    @Override
    public Optional<Tag> findById(Long tagId) {
        return jdbcTemplate.query(READ_TAG_BY_ID, tagMapper, tagId).stream().findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(READ_TAG_BY_NAME, tagMapper, name).stream().findAny();
    }

    @Override
    public void update(Tag tag) {
        jdbcTemplate.update(UPDATE_TAG, tag.getName(), tag.getId());
    }

    @Override
    public void delete(Long tageId) {
        jdbcTemplate.update(DELETE_TAG, tageId);
    }

    @Override
    public List<Tag> getTagsByCertificateId(Long certificateId) {
        return jdbcTemplate.query(READ_TAGS_BY_CERTIFICATE_ID, tagMapper, certificateId);
    }

    @Override
    public void bindWithCertificate(Long certificateId, Long tagId) {
        jdbcTemplate.update(BIND_TAG, certificateId, tagId);
    }

    @Override
    public void unbindTagsFromCertificate(Long certificateId) {
        jdbcTemplate.update(UNBIND_TAGS, certificateId);
    }
}
