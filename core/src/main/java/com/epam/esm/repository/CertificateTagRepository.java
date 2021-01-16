package com.epam.esm.repository;

public interface CertificateTagRepository {
    void bindWithCertificate(Long certificateId, Long tagId);

    void unbindTagsFromCertificate(Long certificateId);
}
