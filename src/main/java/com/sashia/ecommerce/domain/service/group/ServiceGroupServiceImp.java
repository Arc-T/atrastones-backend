package com.sashia.ecommerce.domain.service.group;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupCreateDTO;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupDTO;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupSearchDTO;
import com.sashia.ecommerce.domain.service.group.common.ServiceGroupUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceGroupServiceImp implements ServiceGroupService {

    private final ServiceGroupRepository serviceGroupRepository;

    public ServiceGroupServiceImp(ServiceGroupRepository serviceGroupRepository) {
        this.serviceGroupRepository = serviceGroupRepository;
    }

    @Override
    @Transactional
    public Long create(ServiceGroupCreateDTO serviceGroup) {
        return serviceGroupRepository.create(serviceGroup);
    }

    @Override
    public ServiceGroupDTO get(Long id) {
        return serviceGroupRepository.get(id).map(ServiceGroupDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("serviceGroup.not.found"));
    }

    @Override
    public Page<ServiceGroupDTO> getAll(Pageable pageable, ServiceGroupSearchDTO search) {
        return serviceGroupRepository.getAll(pageable, search).map(ServiceGroupDTO::toDTO);
    }

    @Override
    @Transactional
    public void edit(Long id, ServiceGroupUpdateDTO serviceGroup) {
        if (!serviceGroupRepository.exists(id))
            throw new InvalidResourceException("serviceGroup.not.found");

        serviceGroupRepository.update(id, serviceGroup);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        if (!serviceGroupRepository.exists(id))
            throw new InvalidResourceException("serviceGroup.not.found");

        serviceGroupRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        return serviceGroupRepository.exists(id);
    }

}
