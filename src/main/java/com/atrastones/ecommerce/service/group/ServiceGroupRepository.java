package com.atrastones.ecommerce.service.group;

import com.atrastones.ecommerce.service.group.common.ServiceGroupCreateDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupSearchDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupUpdateDTO;
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
