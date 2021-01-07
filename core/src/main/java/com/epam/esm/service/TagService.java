package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    long createTag(TagDto tagDto);

    List<TagDto> getTags();

    TagDto getTagById(long tagId);

    TagDto updateTag(long tagId, TagDto tagDto);

    int deleteTag(long tagId);
}
