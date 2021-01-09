package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto createTag(TagDto tagDto);

    List<TagDto> getTags();

    TagDto getTagById(long tagId);

    void deleteTag(long tagId);
}
