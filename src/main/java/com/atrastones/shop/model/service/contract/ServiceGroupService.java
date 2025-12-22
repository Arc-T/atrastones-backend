package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.search.ServiceGroupSearch;
import com.atrastones.shop.dto.ServiceGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServiceGroupService {

    Optional<ServiceGroupDTO> get(Long id);

    List<ServiceGroupDTO> getAll(ServiceGroupSearch filter);

    Page<ServiceGroupDTO> getAllPageable(Pageable pageable, ServiceGroupSearch filter);

    void remove(Long id);

    Long save(ServiceGroupDTO service);

    void edit(Long id, ServiceGroupDTO service);

}
