package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final GiftCertificateRepository giftCertificateRepository;
    // private final GiftCertificateService giftCertificateService;

    @GetMapping
    public List<GiftCertificate> getCertificates() {
        //todo replace onto service
        return giftCertificateRepository.findAll();
    }
}
