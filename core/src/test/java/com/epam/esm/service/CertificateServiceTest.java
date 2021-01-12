package com.epam.esm.service;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.impl.CertificateConverterImpl;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    TagService tagService;
    GiftCertificateRepository certificateRepository;
    GiftCertificateService giftCertificateService;
    GiftCertificate certificate;

    {
        certificate = new GiftCertificate();
        certificate.setId(1L);
        certificate.setName("name test 1");
        certificate.setDescription("description test 1");
        certificate.setPrice(new BigDecimal(100));
        certificate.setDuration(5);
    }

    @BeforeEach
    public void setUp() {
        tagService = mock(TagService.class);
        certificateRepository = mock(GiftCertificateRepository.class);
        giftCertificateService = new GiftCertificateServiceImpl(certificateRepository, tagService,
                new CertificateConverterImpl());

    }

    @Test
    void save() {
        when(certificateRepository.findById(any())).thenReturn(Optional.ofNullable(certificate));
        when(tagService.getTagsByCertificateId(any())).thenReturn(new ArrayList<>());

        GiftCertificateDto certificateDTO = giftCertificateService.getCertificateById(1L);

        assertNotNull(certificateDTO.getId());
        assertEquals("name test 1", certificateDTO.getName());
        assertEquals("description test 1", certificateDTO.getDescription());
        assertEquals(new BigDecimal(100), certificateDTO.getPrice());
        assertEquals((Integer) 5, certificateDTO.getDuration());
    }
}