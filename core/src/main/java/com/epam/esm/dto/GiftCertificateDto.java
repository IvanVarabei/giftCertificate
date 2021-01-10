package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GiftCertificateDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;
    @Positive
    private Integer duration;
    private String createdDate;
    private String updatedDate;
    private List<TagDto> tags;
}
