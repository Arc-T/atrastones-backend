package com.sashia.ecommerce.domain.service;

import com.sashia.ecommerce.domain.service.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.service.common.ServiceDTO;
import com.sashia.ecommerce.domain.service.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.service.common.ServiceUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceService {

    /* ============================= CREATE ============================= */

    Long create(ServiceCreateDTO service);

    /* ============================= READ ============================= */

    ServiceDTO get(Long id);

    Page<ServiceDTO> getAll(Pageable pageable, ServiceSearchDTO filter);

    /* ============================= UPDATE ============================= */

    void edit(Long id, ServiceUpdateDTO service);

    /* ============================= DELETE ============================= */

    void delete(Long id);

}