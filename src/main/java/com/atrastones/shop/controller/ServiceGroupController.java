package com.atrastones.shop.controller;

import com.atrastones.shop.dto.search.ServiceGroupSearch;
import com.atrastones.shop.dto.ServiceGroupDTO;
import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.service.contract.ServiceGroupService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/service-groups")
public class ServiceGroupController {

    private final ServiceGroupService serviceGroupService;

    public ServiceGroupController(ServiceGroupService serviceGroupService) {
        this.serviceGroupService = serviceGroupService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceGroupDTO>> readAll(ServiceGroupSearch search) {
        return ResponseEntity.ok(serviceGroupService.getAll(search));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ServiceGroupDTO>> readAllPageable(Pageable pageable, ServiceGroupSearch search) {
        return ResponseEntity.ok(serviceGroupService.getAllPageable(pageable, search));
    }

    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody ServiceGroupDTO service) {
        return ResponseEntity.created(URI.create("/offers/" + serviceGroupService.save(service)))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ServiceGroupDTO service) {
        serviceGroupService.edit(id, service);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceGroupService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
