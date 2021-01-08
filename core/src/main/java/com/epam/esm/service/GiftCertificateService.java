package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> getCertificates();

    GiftCertificateDto updateCertificate(Long certificateId, GiftCertificateDto giftCertificateDto);
}
