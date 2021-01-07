package com.epam.esm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GiftCertificateDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<TagDto> tags;
}
