package com.sashia.ecommerce.domain.catalog.item.serviceoffering;

import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceOfferingRepository {

    // ================================ CREATE ================================

    long create(ServiceCreateDTO service);

    // ================================ GET ================================

    Optional<ServiceOffering> get(Long id);

    Page<ServiceOffering> getAll(Pageable pageable, ServiceSearchDTO search);

    // ================================ UPDATE ================================

    void update(Long id, ServiceUpdateDTO service);

    // ================================ DELETE ================================

    boolean delete(Long id);

    // ================================ OPERATIONS ================================

    Long count();

    boolean exists(Long id);

}
