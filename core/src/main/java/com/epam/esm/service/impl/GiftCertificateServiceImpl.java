package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final TagService tagService;
    private final GiftCertificateRepository giftCertificateRepository;
    private final CertificateConverter certificateConverter;

    @Override
    public List<GiftCertificateDto> getCertificates() {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll();
        return certificates.stream().map(certificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(Long certificateId, GiftCertificateDto giftCertificateDto) {
        GiftCertificate existed = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
            new ResourceNotFoundException("certificate does not exist + id"));
        existed.setPrice(giftCertificateDto.getPrice());
        // todo without tags
        giftCertificateRepository.update(existed);
        return null;//giftCertificateMapper.toDTO(existed);
    }
}