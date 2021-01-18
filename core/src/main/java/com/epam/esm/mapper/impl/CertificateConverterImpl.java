package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateConverterImpl implements CertificateConverter {
    @Override
    public GiftCertificateDto toDTO(GiftCertificate certificate) {
        if (certificate == null) {
            return null;
        }

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(certificate.getId());
        giftCertificateDto.setName(certificate.getName());
        giftCertificateDto.setDescription(certificate.getDescription());
        giftCertificateDto.setPrice(certificate.getPrice());
        giftCertificateDto.setDuration(certificate.getDuration());
        if (certificate.getCreatedDate() != null) {
            giftCertificateDto.setCreatedDate(certificate.getCreatedDate());
        }
        if (certificate.getUpdatedDate() != null) {
            giftCertificateDto.setUpdatedDate(certificate.getUpdatedDate());
        }
        giftCertificateDto.setTags(toDTOs(certificate.getTags()));

        return giftCertificateDto;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null) {
            return null;
        }

        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        if (giftCertificateDto.getCreatedDate() != null) {
            giftCertificate.setCreatedDate(giftCertificateDto.getCreatedDate());
        }
        if (giftCertificateDto.getUpdatedDate() != null) {
            giftCertificate.setUpdatedDate(giftCertificateDto.getUpdatedDate());
        }
        giftCertificate.setTags(toEntities(giftCertificateDto.getTags()));

        return giftCertificate;
    }

    @Override
    public List<Tag> toEntities(List<TagDto> tagsDto) {
        if (tagsDto == null) {
            return null;
        }

        List<Tag> list = new ArrayList<>(tagsDto.size());
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

        List<TagDto> list = new ArrayList<>(tags.size());
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
