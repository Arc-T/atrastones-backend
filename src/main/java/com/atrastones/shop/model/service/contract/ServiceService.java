package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.search.ServiceSearchDTO;
import com.atrastones.shop.dto.ServiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceService {

    /* ******************************** CRUD ******************************** */

    Optional<ServiceDTO> get(Long id);

    Page<ServiceDTO> getAll(Pageable pageable, ServiceSearchDTO filter);

    void remove(Long id);

    Long save(ServiceDTO service);

    void edit(Long id, ServiceDTO service);

}