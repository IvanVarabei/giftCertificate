package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.TagConverter;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagConverter tagConverter;

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = tagConverter.toEntity(tagDto);
        return tagConverter.toDTO(tagRepository.save(tag));
    }

    @Override
    public List<TagDto> getTags() {
        return tagRepository.findAll().stream().map(tagConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(long tagId) {
        return tagConverter.toDTO(tagRepository.findById(tagId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Requested resource not found (id = %s)", tagId))));
    }

    @Override
    public boolean deleteTag(long tagId) {
        return tagRepository.delete(tagId);
    }
}
