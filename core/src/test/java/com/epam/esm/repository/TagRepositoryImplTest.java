package com.epam.esm.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TagRepositoryImplTest {
    @Autowired
    TagRepository tagRepository;
    @Test
    void createTagSuccess(){
        System.out.println(tagRepository);
    }
}
