package com.epam.esm.integration;

import com.epam.esm.config.DatabaseConfig;
import com.epam.esm.config.WebMvcConfig;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ExceptionDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class, WebMvcConfig.class})
@WebAppConfiguration
class CertificateControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void should_return_created_certificate_having_generated_id() throws Exception {
        GiftCertificateDto certificateDto = new GiftCertificateDto();
        certificateDto.setName("creteCertificate");
        certificateDto.setDescription("test description");
        certificateDto.setPrice(BigDecimal.TEN);
        certificateDto.setDuration(1);
        certificateDto.setTags(List.of(new TagDto(null, "testCreateCertificateTagName")));

        String responseAsString = mockMvc
                .perform(post("/api/certificates")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(certificateDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GiftCertificateDto createdCertificate = objectMapper.readValue(responseAsString, GiftCertificateDto.class);

        assertNotNull(createdCertificate.getId());
        assertNotNull(createdCertificate.getTags().get(0).getId());

        jdbcTemplate.update("delete from tag where name = 'testCreateCertificateTagName'");
        jdbcTemplate.update("delete from gift_certificate where name = 'creteCertificate'");
    }

    @Test
    void should_return_not_empty_list_of_exception_dto_when_crete_non_valid_certificate() throws Exception {
        String responseAsString = mockMvc
                .perform(post("/api/certificates")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new GiftCertificateDto())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ExceptionDto> exceptionDtoList = objectMapper.readValue(responseAsString, new TypeReference<>() {
        });

        assertFalse(exceptionDtoList.isEmpty());
    }

    @Test
    void should_return_not_empty_list_of_certificates_after_get_method() throws Exception {
        jdbcTemplate.update("insert into gift_certificate (id, name, description, price, duration) " +
                "values (94563, 'getCertificates', 'description', 1, 1)");

        String responseAsString = mockMvc
                .perform(get("/api/certificates"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<GiftCertificate> foundCertificates = objectMapper.readValue(responseAsString, new TypeReference<>() {
        });

        assertFalse(foundCertificates.isEmpty());

        jdbcTemplate.update("delete from gift_certificate where id = 94563");
    }

    @Test
    void should_return_certificate_having_specified_id() throws Exception {
        jdbcTemplate.update("insert into gift_certificate (id, name, description, price, duration) " +
                "values (90324, 'getCertificateById', 'description', 1, 1)");

        String responseAsString = mockMvc
                .perform(get("/api/certificates/90324"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GiftCertificateDto foundCertificate = objectMapper.readValue(responseAsString, GiftCertificateDto.class);

        assertEquals(90324, foundCertificate.getId());

        jdbcTemplate.update("delete from gift_certificate where id = 90324");
    }

    @Test
    void should_return_certificate_having_updated_fields_after_update() throws Exception {
        jdbcTemplate.update("insert into gift_certificate (id, name, description, price, duration) " +
                "values (98490, 'updateCertificate', 'description', 1, 1)");
        GiftCertificateDto certificateDto = new GiftCertificateDto();
        certificateDto.setId(98490L);
        certificateDto.setName("updated name");
        certificateDto.setDescription("updated description");
        certificateDto.setPrice(BigDecimal.ONE);
        certificateDto.setDuration(2);
        certificateDto.setTags(List.of(new TagDto(null, "newCreatedTag")));

        String responseAsString = mockMvc
                .perform(put("/api/certificates")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(certificateDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GiftCertificateDto updatedCertificate = objectMapper.readValue(responseAsString, GiftCertificateDto.class);

        assertEquals("updated name", updatedCertificate.getName());
        assertEquals("newCreatedTag", updatedCertificate.getTags().get(0).getName());

        jdbcTemplate.update("delete from tag where name = 'newCreatedTag'");
        jdbcTemplate.update("delete from gift_certificate where id = 98490");
    }

    @Test
    void row_set_should_be_empty_after_delete() throws Exception {
        jdbcTemplate.update("insert into gift_certificate (id, name, description, price, duration) " +
                "values (94915, 'deleteCertificate', 'description', 1, 1)");

        mockMvc.perform(delete("/api/certificates/94915"))
                .andExpect(status().is(204));

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select id from gift_certificate where id = 94915");
        assertFalse(sqlRowSet.next());
    }
}
