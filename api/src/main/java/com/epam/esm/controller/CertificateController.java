package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {
    private final GiftCertificateService giftCertificateService;

    @PostMapping
    public ResponseEntity<GiftCertificateDto> createCertificate(
            @Valid @RequestBody GiftCertificateDto giftCertificateDto,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(giftCertificateService.createCertificate(giftCertificateDto));
    }

    @GetMapping
    public List<GiftCertificateDto> getCertificates(@RequestParam(required = false) List<String> tagName,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String description,
                                                    @RequestParam(required = false) String sortField,
                                                    @RequestParam(required = false) String sortOrder) {
        SearchCertificateDto searchCertificateDto = SearchCertificateDto.builder()
                .tagNames(tagName)
                .name(name)
                .description(description)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .build();
        return giftCertificateService.getCertificates(searchCertificateDto);
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
    public GiftCertificateDto getCertificateById(@PathVariable("certificateId") Long certificateId) {
        return giftCertificateService.getCertificateById(certificateId);
    }

    @PutMapping
    public GiftCertificateDto updateCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updateCertificate(giftCertificateDto);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<GiftCertificateDto> deleteCertificate(@PathVariable("certificateId") Long certificateId) {
        giftCertificateService.deleteCertificate(certificateId);
        return ResponseEntity.noContent().build();
    }
}
