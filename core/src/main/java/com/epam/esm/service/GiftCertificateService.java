package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto saveCertificate(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> getCertificates();

    GiftCertificateDto getCertificateById(long certificateId);

    GiftCertificateDto updateCertificate(Long certificateId, GiftCertificateDto giftCertificateDto);

    boolean deleteCertificate(long certificateId);
}
