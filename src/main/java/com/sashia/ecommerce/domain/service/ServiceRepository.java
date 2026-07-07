package com.sashia.ecommerce.domain.service;

import com.sashia.ecommerce.domain.service.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.service.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.service.common.ServiceUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceRepository {

    // ================================ CREATE ================================

    long create(ServiceCreateDTO service);

    // ================================ GET ================================

    Optional<Service> get(Long id);

    Page<Service> getAll(Pageable pageable, ServiceSearchDTO search);

    // ================================ UPDATE ================================

    void update(Long id, ServiceUpdateDTO service);

    // ================================ DELETE ================================

    boolean delete(Long id);

    // ================================ OPERATIONS ================================

    Long count();

    boolean exists(Long id);

}
