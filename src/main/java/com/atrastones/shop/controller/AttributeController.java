package com.atrastones.shop.controller;

import com.atrastones.shop.dto.search.AttributeSearchDTO;
import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.model.service.contract.AttributeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    public ResponseEntity<Page<AttributeDTO>> readAll(AttributeSearchDTO search, Pageable pageable) {
        return ResponseEntity.ok(attributeService.getAll(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(attributeService.get(id));
    }

    @PostMapping
    public ResponseEntity<AttributeDTO> create(@RequestBody AttributeDTO attributeDTO) {
        return ResponseEntity.created(URI.create("/attributes/" + attributeService.save(attributeDTO)))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attributeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AttributeDTO attributeDTO) {
        attributeService.update(id, attributeDTO);
        return ResponseEntity.ok().build();
    }

}