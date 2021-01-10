package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SearchCertificateDto;
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

    //crtl alt <-
    @Override
    public List<GiftCertificateDto> getCertificates(SearchCertificateDto searchDto) {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll(searchDto);
        certificates.forEach(c -> c.setTags(tagRepository.getTagsByCertificateId(c.getId()))); //new
        return certificates.stream().map(certificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getCertificateById(Long certificateId) {
        return certificateConverter.toDTO(giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId))));
    }

//    @Override
//    @Transactional
//    public GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto) {
//        Long certificateId = giftCertificateDto.getId();
//        GiftCertificate existed = giftCertificateRepository
//                .findById(certificateId)
//                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId)));
//
//        GiftCertificate update = certificateConverter.toEntity(giftCertificateDto);
//        existed.setName(update.getName());
//        existed.setPrice(update.getPrice());
//        existed.setDescription(update.getDescription());
//        //...all fields
//        List<Tag> tags = update.getTags();
//
//        //create method that delete all relations from 3 table where certificate_id = existed.getId()
//
//        tags.forEach(t -> {
//            Tag existedTag = tagRepository.findByName(t.getName()).orElseThrow(() -> new ResourceNotFoundException(""));
//            existedTag.setName(t.getName());
//            //todo check  t.setId(existed.getId());
//            tagRepository.update(existedTag);
//            tagRepository.bindWithCertificate(existed.getId(), t.getId());
//        });
//
//        giftCertificateRepository.update(existed);
//        return certificateConverter.toDTO(existed);
//    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto) {
        Long certificateId = giftCertificateDto.getId();
        GiftCertificate existed = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId)));
        if(giftCertificateDto.getName() != null){
            existed.setName(giftCertificateDto.getName());
        }
        Optional.ofNullable(giftCertificateDto.getDescription())
                .ifPresent(n -> existed.setDescription(giftCertificateDto.getDescription()));
        Optional.ofNullable(giftCertificateDto.getPrice())
                .ifPresent(n -> existed.setPrice(giftCertificateDto.getPrice()));
        Optional.ofNullable(giftCertificateDto.getDuration())
                .ifPresent(n -> existed.setDuration(giftCertificateDto.getDuration()));
        Optional.ofNullable(giftCertificateDto.getTags()).ifPresentOrElse(i -> { },
                () -> giftCertificateDto.setTags(Collections.emptyList()));
        giftCertificateDto.getTags().forEach(t -> {
            t.setId(tagRepository.findByName(t.getName())
                    .orElseGet(() -> tagRepository.save(tagConverter.toEntity(t))).getId());
            if (!tagRepository.isBound(certificateId, t.getId())) {
                tagRepository.bindWithCertificate(certificateId, t.getId());
                existed.getTags().add(tagRepository.findByName(t.getName()).get());
            }
        });
        List.copyOf(existed.getTags()).stream()
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
    public void deleteCertificate(Long certificateId) {
        giftCertificateRepository.findById(certificateId)
                .ifPresentOrElse(t -> giftCertificateRepository.delete(certificateId), () -> {
                    throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId));
                });
    }
}
