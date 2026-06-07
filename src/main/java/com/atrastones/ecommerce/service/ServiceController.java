package com.atrastones.ecommerce.service;

import com.atrastones.ecommerce.service.common.ServiceCreateDTO;
import com.atrastones.ecommerce.service.common.ServiceDTO;
import com.atrastones.ecommerce.service.common.ServiceSearchDTO;
import com.atrastones.ecommerce.service.common.ServiceUpdateDTO;
import com.atrastones.ecommerce.tag.common.TagDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/services")
class ServiceController {

    private final ServiceService serviceService;

    ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_SERVICES')")
    ResponseEntity<Page<ServiceDTO>> readAll(Pageable pageable, ServiceSearchDTO search) {
        return ResponseEntity.ok(serviceService.getAll(pageable, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_SERVICE')")
    ResponseEntity<ServiceDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SERVICE')")
    ResponseEntity<TagDTO> create(@RequestBody @Valid ServiceCreateDTO service) {
        return ResponseEntity.created(URI.create("/services/" + serviceService.create(service)))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_SERVICE')")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ServiceUpdateDTO service) {
        serviceService.edit(id, service);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SERVICE')")
    ResponseEntity<?> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}