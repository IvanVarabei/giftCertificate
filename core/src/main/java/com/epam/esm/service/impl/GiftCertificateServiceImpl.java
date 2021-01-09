package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                new ResourceNotFoundException(String.format("Requested resource not found (id = %s)", certificateId))));
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto) {
        long certificateId = giftCertificateDto.getId();
        GiftCertificate existed = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Requested resource not found (id = %s)", certificateId)));
        existed.setPrice(giftCertificateDto.getPrice());
        // todo without tags
        giftCertificateRepository.update(existed);
        return certificateConverter.toDTO(existed);
    }

    @Override
    public boolean deleteCertificate(long certificateId) {
        return giftCertificateRepository.delete(certificateId);
    }
}
