package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto createTag(TagDto tagDto);

    List<TagDto> getTags();

    TagDto getTagById(long tagId);

    TagDto updateTag(TagDto tagDto);

    void deleteTag(long tagId);
}
