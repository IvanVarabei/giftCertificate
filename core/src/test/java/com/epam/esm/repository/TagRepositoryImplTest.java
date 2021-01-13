package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.epam.esm.config.EmbeddedTestConfig.class)
class TagRepositoryImplTest {

    @Autowired
    TagRepository tagRepository;

    @BeforeEach
    public void setUp() {

    }

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
    void del() {
        tagRepository.delete(2L);

        // how to check?
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
    void bind(){

    }

    @Test
    void unbindTagsFromCertificate(){

    }
}