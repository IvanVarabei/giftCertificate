package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
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
    public long createTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        return tagRepository.create(tag);
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
        Tag existed = tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException("certificate does not exist + id"));
        existed.setName(tagDto.getName());
        tagRepository.update(existed);
        TagDto updatedTagDto = new TagDto();
        updatedTagDto.setId(existed.getId());
        updatedTagDto.setName(existed.getName());
        return updatedTagDto;
    }

    @Override
    public int deleteTag(long tagId) {
        return tagRepository.delete(tagId);
    }
}
