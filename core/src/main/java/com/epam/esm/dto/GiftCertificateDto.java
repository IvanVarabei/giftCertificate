package com.epam.esm.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class GiftCertificateDto {
    private Long id;
    @NotBlank
    @Size(min=2, max=30)
    private String name;
    @NotBlank
    private String description;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;
    @Positive
    private Integer duration;
    private String createdDate;
    private String updatedDate;
    private List<@Valid TagDto> tags = new ArrayList<>();
}
