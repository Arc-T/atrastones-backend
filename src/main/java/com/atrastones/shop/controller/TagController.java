package com.atrastones.shop.controller;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.service.contract.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> readAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.get(id));
    }

    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody TagDTO category) {
        return ResponseEntity.created(URI.create("/tags/" + tagService.save(category)))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody TagDTO category) {
        tagService.edit(id, category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
