package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.api.search.ServiceGroupSearch;
import com.atrastones.shop.dto.ServiceGroupDTO;
import com.atrastones.shop.model.repository.contract.ServiceGroupRepository;
import com.atrastones.shop.model.service.contract.ServiceGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceGroupServiceImp implements ServiceGroupService {

    private final ServiceGroupRepository serviceGroupRepository;

    public ServiceGroupServiceImp(ServiceGroupRepository serviceGroupRepository) {
        this.serviceGroupRepository = serviceGroupRepository;
    }

    @Override
    public Optional<ServiceGroupDTO> get(Long id) {
        return serviceGroupRepository.get(id).map(ServiceGroupDTO::toDTO);
    }

    @Override
    public List<ServiceGroupDTO> getAll(ServiceGroupSearch search) {
        return serviceGroupRepository.getAll(search).stream().map(ServiceGroupDTO::toDTO).toList();
    }

    @Override
    public Page<ServiceGroupDTO> getAllPageable(Pageable pageable, ServiceGroupSearch search) {
        return serviceGroupRepository.getAllPaginated(pageable, search).map(ServiceGroupDTO::toDTO);
    }

    @Override
    public void remove(Long id) {
        serviceGroupRepository.delete(id);
    }

    @Override
    public Long save(ServiceGroupDTO serviceGroup) {
        return serviceGroupRepository.create(serviceGroup);
    }

    @Override
    public void edit(Long id, ServiceGroupDTO serviceGroup) {
        serviceGroupRepository.update(id, serviceGroup);
    }
}
