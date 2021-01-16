package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.CertificateConverterImpl;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CertificateServiceImplTest {
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
    void should_invoke_tagService_bindTags_when_createCertificate() {
        GiftCertificateDto certificateDto = new GiftCertificateDto();

        giftCertificateService.createCertificate(certificateDto);

        verify(tagService).bindTags(any(GiftCertificate.class));
    }

    @Test
    void should_invoke_tagService_getTagsByCertificateId_two_times_when_findAll() {
        when(certificateRepository.findAll(any())).thenReturn(List.of(new GiftCertificate(), new GiftCertificate()));

        giftCertificateService.getCertificates(null);

        verify(tagService, times(2)).getTagsByCertificateId(any());
    }

    @Test
    void should_throw_ResourceNotFoundException_when_certificate_not_found() {
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.getCertificateById(1L));
    }

    @Test
    void returns_certificate_having_specified_id_when_getCertificateById() {
        when(certificateRepository.findById(any())).thenReturn(Optional.ofNullable(certificate));
        when(tagService.getTagsByCertificateId(any())).thenReturn(new ArrayList<>());

        GiftCertificateDto certificateDTO = giftCertificateService.getCertificateById(1L);

        assertNotNull(certificateDTO.getId());
        assertEquals("name test 1", certificateDTO.getName());
        assertEquals("description test 1", certificateDTO.getDescription());
        assertEquals(new BigDecimal(100), certificateDTO.getPrice());
        assertEquals((Integer) 5, certificateDTO.getDuration());
    }

    @Test
    void should_invoke_certificateRepository_update_when_updateCertificate() {
        GiftCertificateDto updateCertificateDto = new GiftCertificateDto();
        updateCertificateDto.setId(certificate.getId());
        when(certificateRepository.findById(certificate.getId())).thenReturn(Optional.of(certificate));

        giftCertificateService.updateCertificate(updateCertificateDto);

        verify(certificateRepository).update(any());
    }

    @Test
    void should_invoke_certificateRepository_delete_when_deleteCertificate() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));

        giftCertificateService.deleteCertificate(1L);

        verify(certificateRepository).delete(1L);
    }

    @Test
    void should_throw_ResourceNotFoundException_when_certificate_not_found_when_delete() {
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.deleteCertificate(1L));
    }
}