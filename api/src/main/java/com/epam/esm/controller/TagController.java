package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.CustomValidationError;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody @Valid TagDto tagDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidationError(bindingResult);
        }
        TagDto createdTagDto = tagService.createTag(tagDto);
        URI locationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTagDto.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(createdTagDto);
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable("tagId") Long tagId) {
        return tagService.getTagById(tagId);
    }

    // We actually won't update tags
    @PutMapping
    public TagDto updateTag(@RequestBody TagDto tagDto) {
        return tagService.updateTag(tagDto);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<TagDto> deleteTagById(@PathVariable("tagId") Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }
}
