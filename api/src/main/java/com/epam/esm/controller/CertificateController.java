package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.SearchCertificateDto;
import com.epam.esm.exception.CustomValidationError;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.CustomEitherNullOrLengthGraterThan1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Slf4j
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@Validated // for @PathVariable
public class CertificateController {
    private final GiftCertificateService giftCertificateService;

    @PostMapping
    public ResponseEntity<GiftCertificateDto> createCertificate(
            @Valid @RequestBody GiftCertificateDto giftCertificateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationError(bindingResult);
        }
        return ResponseEntity.status(CREATED).body(giftCertificateService.createCertificate(giftCertificateDto));
    }

    @GetMapping
    public List<GiftCertificateDto> getCertificates(
            @RequestParam(required = false) List<String> tagName,
            @RequestParam(required = false) @CustomEitherNullOrLengthGraterThan1 String name,
            @RequestParam(required = false) @CustomEitherNullOrLengthGraterThan1 String description,
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
    }

    @GetMapping("/{certificateId}")
    public GiftCertificateDto getCertificateById(@PathVariable("certificateId") @Min(1) Long certificateId) {
        return giftCertificateService.getCertificateById(certificateId);
    }

    @PutMapping
    public GiftCertificateDto updateCertificate(
            @Valid @RequestBody GiftCertificateDto giftCertificateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationError(bindingResult);
        }
        return giftCertificateService.updateCertificate(giftCertificateDto);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<GiftCertificateDto> deleteCertificate(
            @PathVariable("certificateId") @Min(1) Long certificateId) {
        giftCertificateService.deleteCertificate(certificateId);
        return ResponseEntity.noContent().build();
    }
}
