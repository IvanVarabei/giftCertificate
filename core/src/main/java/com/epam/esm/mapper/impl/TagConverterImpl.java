package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagConverter;
import org.springframework.stereotype.Component;

@Component
public class TagConverterImpl implements TagConverter {

    @Override
    public TagDto toDTO(Tag artifact) {
        if ( artifact == null ) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId( artifact.getId() );
        tagDto.setName( artifact.getName() );

        return tagDto;
    }

    @Override
    public Tag toEntity(TagDto dto) {
        if ( dto == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setId( dto.getId() );
        tag.setName( dto.getName() );

        return tag;
    }
}
