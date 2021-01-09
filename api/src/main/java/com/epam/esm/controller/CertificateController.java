package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final GiftCertificateService giftCertificateService;
    @GetMapping("/{certificateId}")
    public GiftCertificateDto getCertificateById(@PathVariable("certificateId") long certificateId){
        return giftCertificateService.getCertificateById(certificateId);
    }
//
//    @GetMapping(value = "/name/{name}")
//    public GiftCertificateDto getCertificateByName()...
//
    @PostMapping("/{certificateId}")
    public GiftCertificateDto updateCertificate(@PathVariable("certificateId") long certificateId,
                                                @RequestBody GiftCertificateDto giftCertificateDto){
        giftCertificateService.updateCertificate(certificateId, giftCertificateDto);
        return null;
    }
//
//    @PostMapping(value = "/update/name/{name}")
//    public GiftCertificateDto updateCertificateById()...


    //sertificateDto List<DTO>
    //localhost:8080/certificates?name='name'&description='description'&sort='asc'
    //not requered
    @GetMapping
    public List<GiftCertificateDto> getCertificates() {
        return giftCertificateService.getCertificates();
    }

    @DeleteMapping("/{certificateId}")
    public boolean deleteCertificate(@PathVariable("certificateId") long certificateId){
        return giftCertificateService.deleteCertificate(certificateId);
    }
}
