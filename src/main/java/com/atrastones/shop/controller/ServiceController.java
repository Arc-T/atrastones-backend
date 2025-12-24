package com.atrastones.shop.controller;

import com.atrastones.shop.dto.ServiceDTO;
import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.dto.search.ServiceSearch;
import com.atrastones.shop.model.service.contract.ServiceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<Page<ServiceDTO>> readAll(Pageable pageable, ServiceSearch search) {
        return ResponseEntity.ok(serviceService.getAll(pageable, search));
    }

    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody ServiceDTO service) {
        return ResponseEntity.created(URI.create("/offers/" + serviceService.save(service)))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ServiceDTO service) {
        serviceService.edit(id, service);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
