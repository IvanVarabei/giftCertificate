package com.epam.esm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class TagDto {
    private Long id;
    @NotBlank
    @Size(min=2, max=30)
    private String name;
}
