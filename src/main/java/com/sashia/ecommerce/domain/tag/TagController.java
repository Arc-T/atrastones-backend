package com.sashia.ecommerce.domain.tag;

import com.sashia.ecommerce.domain.tag.common.TagCreateDTO;
import com.sashia.ecommerce.domain.tag.common.TagDTO;
import com.sashia.ecommerce.domain.tag.common.TagSearchDTO;
import com.sashia.ecommerce.domain.tag.common.TagUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/tags")
class TagController {

    private final TagService tagService;

    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_TAGS')")
    ResponseEntity<Page<TagDTO>> readAll(TagSearchDTO search, Pageable pageable) {
        return ResponseEntity.ok(tagService.getAll(search, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_TAG')")
    ResponseEntity<TagDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TAG')")
    ResponseEntity<TagDTO> create(@RequestBody @Valid TagCreateDTO category) {
        return ResponseEntity.created(URI.create("/tags/" + tagService.create(category)))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_TAG')")
    ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TagUpdateDTO category) {
        tagService.edit(id, category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TAG')")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
