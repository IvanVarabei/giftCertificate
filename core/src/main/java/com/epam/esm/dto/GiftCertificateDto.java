package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto {
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(min=2, max=30)
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "Must be positive")
    @Digits(integer = 7, fraction = 2, message = "Two digits after dot")
    private BigDecimal price;
    @Positive(message = "Must be positive")
    private Integer duration;
    private String createdDate;
    private String updatedDate;
    private List<TagDto> tags;
}
