package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl extends GeneralRepository implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private static final String CREATE_TAG = "insert into tag (name) values (?)";
    private static final String READ_TAG_BY_ID = "select id, name from tag where id = ?";
    private static final String UPDATE_TAG = "update tag set name = ? where id = ?";
    private static final String DELETE_TAG = "delete from tag where id = ?";
    private static final String QUERY_GET_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM tag INNER JOIN certificate_tag ON tag.id = tag_id WHERE gift_certificate_id = ?";

    @Override
    public long create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_TAG, new String[]{"id"});
            setParameters(ps, tag.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }

    //todo
    @Override
    public Optional<Tag> findById(Long tagId) {
        return jdbcTemplate.query(READ_TAG_BY_ID, tagMapper,tagId).stream().findFirst();
    }

    @Override
    public int update(Tag tag) {
        return jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(UPDATE_TAG);
            setParameters(ps, tag.getName(), tag.getId());
            return ps;
        });
    }

    @Override
    public int delete(long tageId) {
        return jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(DELETE_TAG);
            setParameters(ps, tageId);
            return ps;
        });
    }

    @Override
    public List<Tag> getTagsByCertificateId(Long id) {
        if (id == null) {
            return Collections.emptyList();
        }
        return jdbcTemplate.query(QUERY_GET_TAGS_BY_CERTIFICATE_ID, tagMapper, id);
    }
}
