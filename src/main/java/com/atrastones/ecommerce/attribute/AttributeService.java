package com.atrastones.ecommerce.attribute;

import com.atrastones.ecommerce.attribute.common.AttributeCreateDTO;
import com.atrastones.ecommerce.attribute.common.AttributeDTO;
import com.atrastones.ecommerce.attribute.common.AttributeSearchDTO;
import com.atrastones.ecommerce.attribute.common.AttributeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttributeService {

    /* ============================= CREATE ============================= */

    Long create(AttributeCreateDTO attribute);

    /* ============================= READ ============================= */

    AttributeDTO read(Long id);

    Page<AttributeDTO> readAll(Pageable pageable, AttributeSearchDTO search);

    /* ============================= UPDATE ============================= */

    void update(Long id, AttributeUpdateDTO attribute);

    /* ============================= DELETE ============================= */

    void delete(Long id);

    /* ============================ OPERATIONS ============================ */

    boolean exists(Long id);

}