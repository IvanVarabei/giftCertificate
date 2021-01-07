package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public boolean createTag(TagDto tagDto) {
        return false;
    }

    @Override
    public List<TagDto> getTags() {
        return null;
    }

    @Override
    public TagDto getTagById(long tagId) {
        return null;
    }

    @Override
    public TagDto updateTag(long tagId, TagDto tagDto) {
        return null;
    }

    @Override
    public boolean deleteTag(long tagId) {
        return false;
    }
}
