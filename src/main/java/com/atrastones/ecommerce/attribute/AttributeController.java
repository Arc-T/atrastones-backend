package com.atrastones.ecommerce.attribute;

import com.atrastones.ecommerce.attribute.common.AttributeCreateDTO;
import com.atrastones.ecommerce.attribute.common.AttributeDTO;
import com.atrastones.ecommerce.attribute.common.AttributeSearchDTO;
import com.atrastones.ecommerce.attribute.common.AttributeUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/attributes")
class AttributeController {

    private final AttributeService attributeService;

    AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_ATTRIBUTES')")
    ResponseEntity<Page<AttributeDTO>> readAll(Pageable pageable, AttributeSearchDTO search) {
        return ResponseEntity.ok(attributeService.readAll(pageable, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ATTRIBUTE')")
    ResponseEntity<AttributeDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(attributeService.read(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ATTRIBUTE')")
    ResponseEntity<AttributeDTO> create(@RequestBody @Valid AttributeCreateDTO attribute) {
        return ResponseEntity.created(URI.create("/attributes/" + attributeService.create(attribute)))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ATTRIBUTE')")
    ResponseEntity<?> delete(@PathVariable Long id) {
        attributeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ATTRIBUTE')")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AttributeUpdateDTO attribute) {
        attributeService.update(id, attribute);
        return ResponseEntity.noContent().build();
    }

}