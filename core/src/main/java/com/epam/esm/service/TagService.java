package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    TagDto createTag(TagDto tagDto);

    List<TagDto> getTags();

    TagDto getTagById(Long tagId);

    TagDto updateTag(TagDto tagDto);

    void deleteTag(Long tagId);

    List<Tag> processTags(GiftCertificate createdCertificate);

    void unbindTagsFromCertificate(Long id);

    List<Tag> getTagsByCertificateId(Long certificateId);
}
