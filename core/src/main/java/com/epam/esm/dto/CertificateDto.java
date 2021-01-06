package com.epam.esm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CertificateDto {
    private Long id;
    // todo validations @NotNull import javax.validation.constraints.NotNull;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<TagDto> tags;
}
