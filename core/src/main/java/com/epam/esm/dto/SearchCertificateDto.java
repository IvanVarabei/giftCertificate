package com.epam.esm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SearchCertificateDto {
    List<String> tagNames;
    private String name;
    private String description;
    private String sortField;
    private String sortOrder;
}
