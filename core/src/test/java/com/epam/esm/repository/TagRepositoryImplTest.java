package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}