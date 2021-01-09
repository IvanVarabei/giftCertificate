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

    @PostMapping
    public GiftCertificateDto createCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.createCertificate(giftCertificateDto);
    }

    @GetMapping
    public List<GiftCertificateDto> getCertificates(@RequestParam(required = false) String tagName,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(required = false) String sortField,
                                                    @RequestParam(required = false) String sortDirection) {
        return giftCertificateService.getCertificates();
    }

    @GetMapping("/{certificateId}")
    public GiftCertificateDto getCertificateById(@PathVariable("certificateId") long certificateId) {
        return giftCertificateService.getCertificateById(certificateId);
    }

    @PutMapping
    public GiftCertificateDto updateCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updateCertificate(giftCertificateDto);
    }

    @DeleteMapping("/{certificateId}")
    public boolean deleteCertificate(@PathVariable("certificateId") long certificateId) {
        return giftCertificateService.deleteCertificate(certificateId);
    }
}
