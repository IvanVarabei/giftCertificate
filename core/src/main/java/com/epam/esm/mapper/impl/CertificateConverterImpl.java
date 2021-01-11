package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CertificateConverterImpl implements CertificateConverter {

    @Override
    public GiftCertificateDto toDTO(GiftCertificate artifact) {
        if (artifact == null) {
            return null;
        }

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(artifact.getId());
        giftCertificateDto.setName(artifact.getName());
        giftCertificateDto.setDescription(artifact.getDescription());
        giftCertificateDto.setPrice(artifact.getPrice());
        giftCertificateDto.setDuration(artifact.getDuration());
        if (artifact.getCreatedDate() != null) {
            giftCertificateDto.setCreatedDate(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(artifact.getCreatedDate()));
        }
        if (artifact.getUpdatedDate() != null) {
            giftCertificateDto.setUpdatedDate(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(artifact.getUpdatedDate()));
        }
        giftCertificateDto.setTags(toDTOs(artifact.getTags()));

        return giftCertificateDto;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto dto) {
        if (dto == null) {
            return null;
        }

        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(dto.getId());
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        if (dto.getCreatedDate() != null) {
            giftCertificate.setCreatedDate(LocalDateTime.parse(dto.getCreatedDate()));
        }
        if (dto.getUpdatedDate() != null) {
            giftCertificate.setUpdatedDate(LocalDateTime.parse(dto.getUpdatedDate()));
        }
        giftCertificate.setTags(toEntities(dto.getTags()));

        return giftCertificate;
    }

    @Override
    public List<Tag> toEntities(List<TagDto> tagsDto) {
        if (tagsDto == null) {
            return null;
        }

        List<Tag> list = new ArrayList<Tag>(tagsDto.size());
        for (TagDto tagDto : tagsDto) {
            list.add(tagDtoToTag(tagDto));
        }

        return list;
    }

    @Override
    public List<TagDto> toDTOs(List<Tag> tags) {
        if (tags == null) {
            return null;
        }

        List<TagDto> list = new ArrayList<TagDto>(tags.size());
        for (Tag tag : tags) {
            list.add(tagToTagDto(tag));
        }

        return list;
    }

    protected Tag tagDtoToTag(TagDto tagDto) {
        if (tagDto == null) {
            return null;
        }

        Tag tag = new Tag();

        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());

        return tag;
    }

    protected TagDto tagToTagDto(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        return tagDto;
    }
}
