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
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl extends GeneralRepository implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String CREATE_TAG = "insert into tag (name) values (?)";
    private static final String READ_TAG_BY_ID = "select id, name from tag where id = ?";
    private static final String UPDATE_TAG = "update tag set name = ? where id = ?";
    private static final String DELETE_TAG ="delete from tag where id = ?";

    @Override
    public long create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_TAG, new String[] { "id" });
            setParameters(ps, tag.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Optional<Tag> findById(Long tagId) {
        return Optional.of(jdbcTemplate.queryForObject(READ_TAG_BY_ID, new Object[]{tagId}, new TagMapper()));
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
}
