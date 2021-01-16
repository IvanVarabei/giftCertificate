//package com.epam.esm.integration;
//
//import com.epam.esm.config.DatabaseConfig;
//import com.epam.esm.config.WebMvcConfig;
//import com.epam.esm.dto.TagDto;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exception.ExceptionDto;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {DatabaseConfig.class, WebMvcConfig.class})
//@WebAppConfiguration
//class TagControllerTest {
//    @Autowired
//    private WebApplicationContext wac;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    private ObjectMapper objectMapper;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        objectMapper = new ObjectMapper();
//        objectMapper.findAndRegisterModules();
//    }
//
//    @Test
//    void should_return_created_tag_having_generated_id() throws Exception {
//        String responseAsString = mockMvc
//                .perform(post("/api/tags")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(new TagDto(null, "testTagName"))))
//                .andExpect(status().isCreated())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        TagDto createdTag = objectMapper.readValue(responseAsString, TagDto.class);
//
//        assertNotNull(createdTag.getId());
//
//        jdbcTemplate.update("delete from tag where name = 'testTagName'");
//    }
//
//    @Test
//    void should_return_not_empty_list_of_exception_dto_when_crete_non_valid_tag() throws Exception {
//        String responseAsString = mockMvc
//                .perform(post("/api/tags")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(new TagDto(null, "not valid name"))))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        List<ExceptionDto> exceptionDtoList = objectMapper.readValue(responseAsString, new TypeReference<>() {
//        });
//
//        assertFalse(exceptionDtoList.isEmpty());
//    }
//
//    @Test
//    void should_return_not_empty_list_of_tags_when_get_all() throws Exception {
//        jdbcTemplate.update("insert into tag (id, name) VALUES (95678, 'getTagsTest')");
//
//        String responseAsString = mockMvc
//                .perform(get("/api/tags"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        List<Tag> foundTags = objectMapper.readValue(responseAsString, new TypeReference<>() {
//        });
//
//        assertFalse(foundTags.isEmpty());
//
//        jdbcTemplate.update("delete from tag where id = 95678");
//    }
//
//    @Test
//    void should_return_tag_having_specified_id() throws Exception {
//        jdbcTemplate.update("insert into tag (id, name) VALUES (93242, 'testFindById')");
//
//        String responseAsString = mockMvc
//                .perform(get("/api/tags/93242"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        TagDto foundTag = objectMapper.readValue(responseAsString, TagDto.class);
//
//        assertEquals(93242, foundTag.getId());
//
//        jdbcTemplate.update("delete from tag where id = 93242");
//    }
//
//    @Test
//    void row_set_should_be_empty_after_delete() throws Exception {
//        jdbcTemplate.update("insert into tag (id, name) VALUES (97183, 'testDeleteTag')");
//
//        mockMvc.perform(delete("/api/tags/97183"))
//                .andExpect(status().is(204));
//
//        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select id from tag where id = 97183");
//        assertFalse(sqlRowSet.next());
//
//        jdbcTemplate.update("delete from tag where id = 97183");
//    }
//}
