package com.epam.esm.dto;

import java.math.BigDecimal;
import java.util.List;

public class CertificateDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<TagDto> tags;
}
