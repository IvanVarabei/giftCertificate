package com.epam.esm.repository;

import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.dto.search.SortByField;
import com.epam.esm.dto.search.SortOrder;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.epam.esm.config.EmbeddedTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GiftCertificateRepositoryImplTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GiftCertificateRepository certificateRepository;

    @Test
    void should_id_not_be_null_when_save() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("name test 11");
        certificate.setDescription("description test 1");
        certificate.setPrice(new BigDecimal(100));
        certificate.setDuration(5);
        certificateRepository.save(certificate);

        Assertions.assertNotNull(certificate.getId());
    }

    @Test
    void should_return_certificates_corresponding_the_search_dto() {
        SearchCertificateDto searchDto = new SearchCertificateDto(
                List.of("cheap"),
                "e",
                "f",
                SortByField.NAME,
                SortOrder.DESC
        );

        List<GiftCertificate> giftCertificateList = certificateRepository.findAll(searchDto);
        long actualAmount = giftCertificateList.size();
        long checkedAmount = giftCertificateList.stream()
                .filter(c -> c.getName().contains("e"))
                .filter(c -> c.getDescription().contains("f"))
                .count();

        assertEquals(checkedAmount, actualAmount);
    }

    @Test
    void should_find_certificate_having_specified_id() {
        Optional<GiftCertificate> giftCertificateOptional = certificateRepository.findById(1L);

        assertEquals(1L, (long) giftCertificateOptional.get().getId());
    }

    @Test
    @Sql("/sql/insert_certificate_with_id_93443.sql")
    @Order(1)
    void row_set_should_not_be_empty_after_update() {
        GiftCertificate update = new GiftCertificate();
        update.setId(93443L);
        update.setName("updatedName");
        update.setDescription("updatedDescription");
        update.setPrice(BigDecimal.TEN);
        update.setDuration(1);

        certificateRepository.update(update);

        String sql = "select id from gift_certificate where id = 93443 and name = 'updatedName' and price = 10";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        assertTrue(sqlRowSet.next());
    }

    @Test
    @Order(2)
    void row_set_should_be_empty_after_delete() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select id from gift_certificate where id = 93443");
        assertTrue(sqlRowSet.next());

        certificateRepository.delete(93443L);

        sqlRowSet = jdbcTemplate.queryForRowSet("select id from gift_certificate where id = 93443");
        assertFalse(sqlRowSet.next());
    }
}