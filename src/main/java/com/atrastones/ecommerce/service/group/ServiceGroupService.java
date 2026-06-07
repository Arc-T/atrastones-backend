package com.atrastones.ecommerce.service.group;

import com.atrastones.ecommerce.service.group.common.ServiceGroupCreateDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupSearchDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceGroupService {

    /* ============================= CREATE ============================= */

    Long create(ServiceGroupCreateDTO service);

    /* ============================= READ ============================= */

    ServiceGroupDTO get(Long id);

    Page<ServiceGroupDTO> getAll(Pageable pageable, ServiceGroupSearchDTO filter);

    /* ============================= UPDATE ============================= */

    void edit(Long id, ServiceGroupUpdateDTO service);

    /* ============================= DELETE ============================= */

    void remove(Long id);

    /* ============================= OPERATION ============================= */

    boolean exists(Long id);

}
