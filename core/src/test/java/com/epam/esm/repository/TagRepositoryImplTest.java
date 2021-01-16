package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.epam.esm.config.EmbeddedTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagRepositoryImplTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TagRepository tagRepository;

    @Test
    void should_id_not_be_null_when_save() {
        Tag tag = new Tag();
        tag.setName("name test 1");
        tagRepository.save(tag);

        Assertions.assertNotNull(tag.getId());
    }

    @Test
    void should_return_more_than_zero_tags_when_findAll() {
        List<Tag> tags = tagRepository.findAll();

        assertTrue(tags.size() > 0);
    }

    @Test
    void should_be_not_empty_optional_when_findById() {
        Optional<Tag> tagOptional = tagRepository.findById(1L);

        assertEquals(1L, (long) tagOptional.get().getId());
    }

    @Test
    void should_be_the_same_name_when_find_by_name() {
        Optional<Tag> tagOptional = tagRepository.findByName("gym");

        assertEquals("gym", tagOptional.get().getName());
    }

    @Test
    @Sql("/sql/insert_tag_with_id_924984.sql")
    void row_set_should_be_empty_after_delete() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select id from tag where id = 924984");
        assertTrue(sqlRowSet.next());

        tagRepository.delete(924984L);

        sqlRowSet = jdbcTemplate.queryForRowSet("select id from tag where id = 924984");
        assertFalse(sqlRowSet.next());
    }

    @Test
    void should_return_tags_that_related_to_certificate_id() {
        List<Tag> tagsRefToCertificate = tagRepository.getTagsByCertificateId(1L);
        List<Long> expectedTagIdList = tagsRefToCertificate.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());

        assertEquals(List.of(1L, 2L), expectedTagIdList);
    }

    @Test
    @Sql("/sql/insert_certificate_id_97777_tag_id_98888.sql")
    @Order(1)
    void row_set_should_not_be_empty_after_bind() {
        String sql = "select tag_id from certificate_tag where gift_certificate_id = 97777 and tag_id = 98888";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        assertFalse(sqlRowSet.next());

        tagRepository.bindWithCertificate(97777L, 98888L);

        sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        assertTrue(sqlRowSet.next());
    }

    @Test
    @Order(2)
    void row_set_should_be_empty_after_unbind() {
        String sql = "select tag_id from certificate_tag where gift_certificate_id = 97777 and tag_id = 98888";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        assertTrue(sqlRowSet.next());

        tagRepository.unbindTagsFromCertificate(97777L);

        sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        assertFalse(sqlRowSet.next());
    }
}