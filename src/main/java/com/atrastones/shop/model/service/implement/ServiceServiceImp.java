package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.search.ServiceSearch;
import com.atrastones.shop.dto.ServiceDTO;
import com.atrastones.shop.model.repository.contract.ServiceRepository;
import com.atrastones.shop.model.service.contract.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ServiceServiceImp implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImp(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Optional<ServiceDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<ServiceDTO> getAll(Pageable pageable, ServiceSearch search) {
        return serviceRepository.getAll(pageable, search)
                .map(ServiceDTO::toDTO);
    }

    @Override
    public void remove(Long id) {
        serviceRepository.delete(id);
    }

    @Override
    @Transactional
    public Long save(ServiceDTO service) {
        return serviceRepository.create(service);
    }

    @Override
    @Transactional
    public void edit(Long id, ServiceDTO service) {
        serviceRepository.update(id, service);
    }

}
