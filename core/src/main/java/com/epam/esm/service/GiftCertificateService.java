package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto createCertificate(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> getCertificates();

    GiftCertificateDto getCertificateById(long certificateId);

    GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto);

    boolean deleteCertificate(long certificateId);
}
