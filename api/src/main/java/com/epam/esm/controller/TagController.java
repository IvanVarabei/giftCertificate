package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/create")
    public boolean createTag(@RequestBody TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable long tagId) {
        return tagService.getTagById(tagId);
    }

    @PutMapping("/{tagId}")
    public TagDto updateTag(@PathVariable long tagId, @RequestBody TagDto tagDto) {
        return tagService.updateTag(tagId, tagDto);
    }

    @DeleteMapping("/{tagId}")
    public boolean deleteTagById(@PathVariable long tagId) {
        return tagService.deleteTag(tagId);
    }
}
