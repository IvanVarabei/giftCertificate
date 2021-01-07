package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.security.cert.Certificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> fingById(Long certificateId);
}
