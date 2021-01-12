package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.epam.esm.config.DatabaseTestConfig.class)
@PropertySource("classpath:application-test.properties")
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
}