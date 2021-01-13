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
    @Pattern(regexp = "\\w{2,64}")
    private String name;
    @NotBlank
    @Pattern(regexp = "\\w{2,512}")
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
