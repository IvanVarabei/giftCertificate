package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    long create(Tag tag);

    List<Tag> findAll();

    Optional<Tag> findById(Long tagId);

    int update(Tag tag);

    int delete(long tagId);

    List<Tag> getTagsByCertificateId(Long id);
}
