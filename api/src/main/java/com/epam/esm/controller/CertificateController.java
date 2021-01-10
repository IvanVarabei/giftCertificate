package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public List<GiftCertificateDto> getCertificates(@RequestParam(required = false) List<String> tagName,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(required = false) String sortField,
                                                    @RequestParam(required = false) String sortDirection) {

        return giftCertificateService.getCertificates();
        //wrapper
//        -- select * from gift_certificate where name ilike %'?'%
//                --                                      or description ilike %'?'%
//
//                -- select * from gift_certificate
//                -- join certificate_tag ct on gift_certificate.id = ct.gift_certificate_id
//                -- join tag t on t.id = ct.tag_id
//                -- and t.name ilike '%ch%'
//                -- and t.name ilike '%g%'
//
//        select * from gift_certificate cc
//        join certificate_tag ct on cc.id = ct.gift_certificate_id
//        join tag t on t.id = ct.tag_id
//        and t.name ilike '%ch%'
//        and cc.description ilike '%g%'
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
    public ResponseEntity<GiftCertificateDto> deleteCertificate(@PathVariable("certificateId") long certificateId) {
        giftCertificateService.deleteCertificate(certificateId);
        return ResponseEntity.noContent().build();
    }
}
