package com.sashia.ecommerce.domain.attribute;

import com.sashia.ecommerce.domain.attribute.dto.AttributeCreateRequest;
import com.sashia.ecommerce.domain.attribute.dto.AttributeResponse;
import com.sashia.ecommerce.domain.attribute.dto.AttributeSearchRequest;
import com.sashia.ecommerce.domain.attribute.dto.AttributeUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AttributeService {

    /* ============================= CREATE ============================= */

    Long create(AttributeCreateRequest attribute);

    /* ============================= READ ============================= */

    Optional<AttributeResponse> read(Long id);

    Page<AttributeResponse> readAll(Pageable pageable, AttributeSearchRequest search);

    /* ============================= UPDATE ============================= */

    void update(Long id, AttributeUpdateRequest attribute);

    /* ============================= DELETE ============================= */

    void delete(Long id);

    /* ============================ OPERATIONS ============================ */

    boolean exists(Long id);

}