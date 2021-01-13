package com.epam.esm.dto;

import com.epam.esm.dto.search.SortByField;
import com.epam.esm.dto.search.SortOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SearchCertificateDto {
    private List<String> tagNames;
    private String name;
    private String description;
    private SortByField sortByField;
    private SortOrder sortOrder;
}
