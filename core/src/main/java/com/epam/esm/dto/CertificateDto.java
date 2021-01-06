package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CertificateDto {
    //id
    private Long giftCertificateId;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<TagDto> tags;
}
