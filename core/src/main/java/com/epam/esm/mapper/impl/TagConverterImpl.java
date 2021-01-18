package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagConverter;
import org.springframework.stereotype.Component;

@Component
public class TagConverterImpl implements TagConverter {
    @Override
    public TagDto toDTO(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        return tagDto;
    }

    @Override
    public Tag toEntity(TagDto tagDto) {
        if (tagDto == null) {
            return null;
        }

        Tag tag = new Tag();

        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());

        return tag;
    }
}
