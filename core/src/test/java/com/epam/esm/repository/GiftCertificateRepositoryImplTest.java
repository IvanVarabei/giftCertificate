package com.epam.esm.repository;

import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.dto.search.SortByField;
import com.epam.esm.dto.search.SortOrder;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.epam.esm.config.EmbeddedTestConfig.class)
class GiftCertificateRepositoryImplTest {

    @Autowired
    GiftCertificateRepository certificateRepository;

    @BeforeEach
    public void setUp() {

    }

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
    void update(){

    }

    @Test
    void delete(){

    }
}