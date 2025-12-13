package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.api.search.ServiceSearch;
import com.atrastones.shop.dto.ServiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    /* ******************************** CRUD ******************************** */

    Optional<ServiceDTO> get(Long id);

    List<ServiceDTO> getAll(ServiceSearch filter);

    Page<ServiceDTO> getAllPageable(Pageable pageable, ServiceSearch filter);

    void remove(Long id);

    Long save(ServiceDTO service);

    void edit(Long id, ServiceDTO service);

}