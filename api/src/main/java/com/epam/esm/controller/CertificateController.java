package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {
//    @GetMapping
//    public List<GiftCertificateDto> getCertificates()...
//
//    @GetMapping(value = "/id/{id}")
//    public GiftCertificateDto getCertificateById()...
//
//    @GetMapping(value = "/name/{name}")
//    public GiftCertificateDto getCertificateByName()...
//
//    @PostMapping(value = "/update/id/{id}")
//    public GiftCertificateDto updateCertificateId()...
//
//    @PostMapping(value = "/update/name/{name}")
//    public GiftCertificateDto updateCertificateById()...
    private final GiftCertificateService giftCertificateService;

    //sertificateDto List<DTO>
    @GetMapping
    public List<GiftCertificate> getCertificates() {
        log.info("hello--------------------------------------------------------------------------------------");
        return giftCertificateService.findAll();
    }

    @GetMapping(value = "/student")
    public String getCertific() {
        return "giftCertificateService.findAll()";
    }
}
