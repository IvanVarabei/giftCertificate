package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag save(Tag tag);

    List<Tag> findAll();

    Optional<Tag> findById(Long tagId);

    boolean update(Tag tag);

    boolean delete(long tagId);

    List<Tag> getTagsByCertificateId(Long id);
}
