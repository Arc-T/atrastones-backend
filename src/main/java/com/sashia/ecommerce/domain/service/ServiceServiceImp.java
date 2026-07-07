package com.sashia.ecommerce.domain.service;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.service.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.service.common.ServiceDTO;
import com.sashia.ecommerce.domain.service.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.service.common.ServiceUpdateDTO;
import com.sashia.ecommerce.domain.service.group.ServiceGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceServiceImp implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceGroupService serviceGroupService;

    public ServiceServiceImp(ServiceRepository serviceRepository, ServiceGroupService serviceGroupService) {
        this.serviceRepository = serviceRepository;
        this.serviceGroupService = serviceGroupService;
    }

    @Override
    @Transactional
    public Long create(ServiceCreateDTO service) {
        if (!serviceGroupService.exists(service.serviceGroupId()))
            throw new InvalidResourceException("service.serviceGroup.invalid");

        return serviceRepository.create(service);
    }

    @Override
    public ServiceDTO get(Long id) {
        return serviceRepository.get(id).map(ServiceDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("service.not.found"));
    }

    @Override
    public Page<ServiceDTO> getAll(Pageable pageable, ServiceSearchDTO search) {
        return serviceRepository.getAll(pageable, search).map(ServiceDTO::toDTO);
    }

    @Override
    @Transactional
    public void edit(Long id, ServiceUpdateDTO service) {
        if (!serviceRepository.exists(id))
            throw new InvalidResourceException("service.not.found");

        if (!serviceGroupService.exists(service.serviceGroupId()))
            throw new InvalidResourceException("service.serviceGroup.invalid");

        serviceRepository.update(id, service);
    }

    @Override
    public void delete(Long id) {
        if (!serviceRepository.exists(id))
            throw new InvalidResourceException("service.not.found");

        serviceRepository.delete(id);
    }

}
