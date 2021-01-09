package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable("tagId") long tagId) {
        return tagService.getTagById(tagId);
    }

    @PutMapping
    public boolean updateTag(@RequestBody TagDto tagDto) {
        return tagService.updateTag(tagDto);
    }

    @DeleteMapping("/{tagId}")
    public boolean deleteTagById(@PathVariable("tagId") long tagId) {
        return tagService.deleteTag(tagId);
    }
}
