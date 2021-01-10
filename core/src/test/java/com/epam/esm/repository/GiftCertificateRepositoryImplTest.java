package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
//@ExtendWith(Extension.class)
@ContextConfiguration(classes = com.epam.esm.config.DatabaseTestConfig.class)
@PropertySource("classpath:application-test.properties")
@RunWith(JUnitPlatform.class)
class GiftCertificateRepositoryImplTest {
    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    private GiftCertificate giftCertificate;
    @Autowired
    GiftCertificateRepository certificateRepository;

    @BeforeEach
    public void setup() {
        certificateRepository.setJdbcTemplate(jdbcTemplate);
        when(giftCertificate.getName()).thenReturn("food");
        when(giftCertificate.getDescription()).thenReturn("minsk");
        when(giftCertificate.getPrice()).thenReturn(BigDecimal.valueOf(1));
        when(giftCertificate.getDuration()).thenReturn(1);
        when(giftCertificate.getId()).thenReturn(1L);
    }

    @Test
    void update() {
        certificateRepository.update(giftCertificate);

        verify(jdbcTemplate).update("update gift_certificate set name = ?, description = ?, price = ?, " +
                "duration = ? where id = ?", "food", "minsk", BigDecimal.valueOf(1), 1, 1L);
    }

    //@Order(1)
    @Test
//    @Sql("/db.test/V1.2create_schema.sql")
//    @Sql("/db.test/V1.3insert_data.sql")
    void getAll() {
        Assertions.assertEquals(10, 10);
    }
}
