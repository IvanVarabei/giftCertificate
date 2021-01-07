package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    GiftCertificateDto updateCertificate(Long certificateId, GiftCertificateDto giftCertificateDto);
}
