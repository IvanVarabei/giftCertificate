package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.NonRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {
    private final GiftCertificateService giftCertificateService;

    @PostMapping
    public ResponseEntity<GiftCertificateDto> createCertificate(
            @Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        return ResponseEntity.status(CREATED).body(giftCertificateService.createCertificate(giftCertificateDto));
    }

    @GetMapping 
    public List<GiftCertificateDto> getCertificates(
            @RequestParam(required = false) List<@NonRequired(regex = "\\w{2,30}") String> tagName,
            @RequestParam(required = false) @NonRequired(regex = "\\w{2,30}") String name,
            @RequestParam(required = false) @NonRequired(regex = "\\w{2,30}") String description,
            @RequestParam(required = false) @NonRequired(regex = "(last_update_date)|(name)") String sortField,
            @RequestParam(required = false) @NonRequired(regex = "(asc)|(desc)") String sortOrder) {
        SearchCertificateDto searchCertificateDto = SearchCertificateDto.builder()
                .tagNames(tagName)
                .name(name)
                .description(description)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .build();
        return giftCertificateService.getCertificates(searchCertificateDto);
    }

    @GetMapping("/{certificateId}")
    public GiftCertificateDto getCertificateById(@PathVariable("certificateId") @Min(1) Long certificateId) {
        return giftCertificateService.getCertificateById(certificateId);
    }

    @PutMapping
    public GiftCertificateDto updateCertificate(@Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updateCertificate(giftCertificateDto);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<GiftCertificateDto> deleteCertificate(
            @PathVariable("certificateId") @Min(1) Long certificateId) {
        giftCertificateService.deleteCertificate(certificateId);
        return ResponseEntity.noContent().build();
    }
}
