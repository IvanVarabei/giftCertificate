package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    GiftCertificate save(GiftCertificate giftCertificate);

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(Long certificateId);

    void update(GiftCertificate giftCertificate);

    void delete(long giftCertificateId);
}
