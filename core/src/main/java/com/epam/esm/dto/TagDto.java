package com.epam.esm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

//@Getter
//@Setter
//@NoArgsConstructor
public class TagDto {
    private Long id;
    @NotNull
    private String name;

    public TagDto(){

    }

    public TagDto(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagDto tagDto = (TagDto) o;

        if (id != null ? !id.equals(tagDto.id) : tagDto.id != null) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
