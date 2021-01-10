package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.mapper.TagConverter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final CertificateConverter certificateConverter;
    private final TagConverter tagConverter;

    @Override
    public GiftCertificateDto createCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = certificateConverter.toEntity(giftCertificateDto);
        giftCertificateRepository.save(giftCertificate);
        giftCertificate.getTags().forEach(t -> {
            Optional<Tag> tagOptional = tagRepository.findByName(t.getName());
            if (tagOptional.isEmpty()) {
                tagRepository.save(t);
            } else {
                t.setId(tagOptional.get().getId());
            }
            tagRepository.bindWithCertificate(giftCertificate.getId(), t.getId());
        });
        return certificateConverter.toDTO(giftCertificate);
    }

    // unbind service level
    //crtl alt <-
    @Override
    public List<GiftCertificateDto> getCertificates() {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll();
        certificates.forEach(c -> c.setTags(tagRepository.getTagsByCertificateId(c.getId()))); //new
        return certificates.stream().map(certificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getCertificateById(long certificateId) {
        return certificateConverter.toDTO(giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId))));
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto) {
        long certificateId = giftCertificateDto.getId();
        GiftCertificate existed = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId)));
        existed.setTags(tagRepository.getTagsByCertificateId(giftCertificateDto.getId()));
        if (giftCertificateDto.getName() != null) {
            existed.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            existed.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            existed.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != existed.getDuration()) {
            existed.setDuration(giftCertificateDto.getDuration());
        }
        if (giftCertificateDto.getTags() == null) {
            giftCertificateDto.setTags(Collections.emptyList());
        }
        giftCertificateDto.getTags().forEach(t -> {
            Optional<Tag> tagOptional = tagRepository.findByName(t.getName());
            if (tagOptional.isEmpty()) {
                Tag t1 = tagRepository.save(tagConverter.toEntity(t));
                t.setId(t1.getId());
            } else {
                t.setId(tagOptional.get().getId());
            }
            if (!tagRepository.isBound(certificateId, t.getId())) {
                tagRepository.bindWithCertificate(certificateId, t.getId());
                existed.getTags().add(tagRepository.findByName(t.getName()).get());
            }
        });
        new ArrayList<>(existed.getTags()).stream()
                .filter(t -> !giftCertificateDto.getTags().stream().map(TagDto::getName)
                        .collect(Collectors.toList()).contains(t.getName()))
                .forEach(t -> {
                    tagRepository.unbindWithCertificate(existed.getId(), t.getId());
                    existed.getTags().removeIf(t::equals);
                });
        giftCertificateRepository.update(existed);
        return certificateConverter.toDTO(existed);
    }

    @Override
    public void deleteCertificate(long certificateId) {
        giftCertificateRepository.findById(certificateId)
                .ifPresentOrElse(t -> giftCertificateRepository.delete(certificateId), () -> {
                    throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId));
                });
    }
}
