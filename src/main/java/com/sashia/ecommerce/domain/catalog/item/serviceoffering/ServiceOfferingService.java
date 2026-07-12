package com.sashia.ecommerce.domain.catalog.item.serviceoffering;

import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceOfferingService {

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