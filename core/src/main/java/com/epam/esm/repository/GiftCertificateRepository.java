package com.epam.esm.repository;

import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.entity.GiftCertificate;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    ZoneId defaultZone = ZoneOffset.UTC;

    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAll(SearchCertificateDto searchDto);

    Optional<GiftCertificate> findById(Long certificateId);

    void update(GiftCertificate giftCertificate);

    void delete(Long giftCertificateId);
}
