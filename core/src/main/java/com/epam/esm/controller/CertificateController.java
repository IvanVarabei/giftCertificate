package com.epam.esm.controller;

import com.epam.esm.config.ModelSpringConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    //@GetMapping(produces = "application/json")
    //sertificateDto List<DTO>
    @GetMapping
    public List<GiftCertificate> getCertificates() {
        return giftCertificateRepository.findAll();
    }
}

//    AnnotationConfigApplicationContext annotationContext =
//                new AnnotationConfigApplicationContext(ModelSpringConfig.class);
