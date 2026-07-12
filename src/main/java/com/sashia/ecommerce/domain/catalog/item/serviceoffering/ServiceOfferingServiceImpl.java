package com.sashia.ecommerce.domain.catalog.item.serviceoffering;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceUpdateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.ServiceGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ServiceGroupService serviceGroupService;

    public ServiceOfferingServiceImpl(ServiceOfferingRepository serviceOfferingRepository, ServiceGroupService serviceGroupService) {
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.serviceGroupService = serviceGroupService;
    }

    @Override
    @Transactional
    public Long create(ServiceCreateDTO service) {
        if (!serviceGroupService.exists(service.serviceGroupId()))
            throw new InvalidResourceException("service.serviceGroup.invalid");

        return serviceOfferingRepository.create(service);
    }

    @Override
    public ServiceDTO get(Long id) {
        return serviceOfferingRepository.get(id).map(ServiceDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("service.not.found"));
    }

    @Override
    public Page<ServiceDTO> getAll(Pageable pageable, ServiceSearchDTO search) {
        return serviceOfferingRepository.getAll(pageable, search).map(ServiceDTO::toDTO);
    }

    @Override
    @Transactional
    public void edit(Long id, ServiceUpdateDTO service) {
        if (!serviceOfferingRepository.exists(id))
            throw new InvalidResourceException("service.not.found");

        if (!serviceGroupService.exists(service.serviceGroupId()))
            throw new InvalidResourceException("service.serviceGroup.invalid");

        serviceOfferingRepository.update(id, service);
    }

    @Override
    public void delete(Long id) {
        if (!serviceOfferingRepository.exists(id))
            throw new InvalidResourceException("service.not.found");

        serviceOfferingRepository.delete(id);
    }

}
