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

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final TagService tagService;
    private final GiftCertificateRepository giftCertificateRepository;
    private final CertificateConverter giftCertificateMapper;

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepository.findAll();
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(Long certificateId, GiftCertificateDto giftCertificateDto) {
        GiftCertificate existed = giftCertificateRepository.fingById(certificateId).orElseThrow(() ->
            new ResourceNotFoundException("certificate does not exist + id"));
        existed.setPrice(giftCertificateDto.getPrice());
        // todo without tags
        return giftCertificateMapper.toDTO(existed);
    }

}
