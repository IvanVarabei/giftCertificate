package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {
    boolean createTag(TagDto tagDto);

    List<TagDto> getTags();

    TagDto getTagById(long tagId);

    TagDto updateTag(long tagId, TagDto tagDto);

    boolean deleteTag(long tagId);
}
