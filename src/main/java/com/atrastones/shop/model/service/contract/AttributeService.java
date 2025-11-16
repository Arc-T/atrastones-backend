package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.api.search.AttributeSearch;
import com.atrastones.shop.dto.AttributeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttributeService {

    /* ******************************** CRUD ******************************** */

    void delete(Long id);

    Long save(AttributeDTO attribute);

    void update(Long id, AttributeDTO attribute);

    AttributeDTO get(Long id);

    List<AttributeDTO> getAll(AttributeSearch search);

    Page<AttributeDTO> getAllPaginated(Pageable pageable);

    List<AttributeDTO> getAllByCategoryId(Long categoryId);

    /* ******************************** OPERATIONS ******************************** */

    boolean exists(Long id);

}