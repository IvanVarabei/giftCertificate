package com.epam.esm.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@Configuration
@ExtendWith(SpringExtension.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = com.epam.esm.config.DatabaseTestConfig.class)
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
class TagRepositoryImplTest {
    @Autowired
    TagRepository tagRepository;
    @Test
    void createTagSuccess(){
        System.out.println(tagRepository);
    }
}
