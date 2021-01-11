package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;
    private final CertificateConverter certificateConverter;

    @Override
    public GiftCertificateDto createCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdCertificate = certificateConverter.toEntity(giftCertificateDto);
        createdCertificate.setId(null);
        giftCertificateRepository.save(createdCertificate);
        List<Tag> tags = tagService.processTags(createdCertificate);
        createdCertificate.setTags(tags);
        return certificateConverter.toDTO(createdCertificate);
    }

    @Override
    public List<GiftCertificateDto> getCertificates(SearchCertificateDto searchDto) {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll(searchDto);
        certificates.forEach(c -> c.setTags(tagService.getTagsByCertificateId(c.getId())));
        return certificates.stream().map(certificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getCertificateById(Long certificateId) {
        GiftCertificate certificate = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, certificateId)));
        certificate.setTags(tagService.getTagsByCertificateId(certificateId));
        return certificateConverter.toDTO(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto updateDto) {
        GiftCertificate existed = giftCertificateRepository
                .findById(updateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, updateDto.getId())));
        GiftCertificate update = certificateConverter.toEntity(updateDto);
        existed.setName(update.getName());
        existed.setPrice(update.getPrice());
        existed.setDescription(update.getDescription());
        existed.setDuration(update.getDuration());
        existed.setUpdatedDate(LocalDateTime.now());
        giftCertificateRepository.update(existed);
        tagService.unbindTagsFromCertificate(existed.getId());
        existed.setTags(tagService.processTags(update));
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
