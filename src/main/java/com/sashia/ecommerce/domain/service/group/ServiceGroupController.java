package com.sashia.ecommerce.domain.service.group;

import com.sashia.ecommerce.domain.service.group.common.ServiceGroupCreateDTO;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupDTO;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupSearchDTO;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupUpdateDTO;
import com.sashia.ecommerce.domain.tag.common.TagDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/service-groups")
class ServiceGroupController {

    private final ServiceGroupService serviceGroupService;

    ServiceGroupController(ServiceGroupService serviceGroupService) {
        this.serviceGroupService = serviceGroupService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_SERVICE_GROUPS')")
    ResponseEntity<Page<ServiceGroupDTO>> readAll(Pageable pageable, ServiceGroupSearchDTO search) {
        return ResponseEntity.ok(serviceGroupService.getAll(pageable, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_SERVICE_GROUP')")
    ResponseEntity<ServiceGroupDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(serviceGroupService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SERVICE_GROUP')")
    ResponseEntity<TagDTO> create(@RequestBody @Valid ServiceGroupCreateDTO service) {
        return ResponseEntity.created(URI.create("/service-groups/" + serviceGroupService.create(service)))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_SERVICE_GROUP')")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ServiceGroupUpdateDTO service) {
        serviceGroupService.edit(id, service);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SERVICE_GROUP')")
    ResponseEntity<?> delete(@PathVariable Long id) {
        serviceGroupService.remove(id);
        return ResponseEntity.noContent().build();
    }

}