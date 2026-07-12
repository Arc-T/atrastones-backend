package com.sashia.ecommerce.domain.catalog.item.serviceoffering.group;

import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common.ServiceGroupCreateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common.ServiceGroupSearchDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common.ServiceGroupUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceGroupRepository {

    // =============================== CREATE ===============================

    long create(ServiceGroupCreateDTO service);

    // =============================== SELECT ===============================

    Optional<ServiceGroup> get(Long id);

    Page<ServiceGroup> getAll(Pageable pageable, ServiceGroupSearchDTO search);

    // =============================== UPDATE ===============================

    void update(Long id, ServiceGroupUpdateDTO service);

    // =============================== DELETE ===============================

    boolean delete(Long id);

    // =============================== OPERATIONS ===============================

    Long count();

    boolean exists(Long id);

}
