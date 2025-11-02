package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.AttributeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttributeService {

    /* ******************************** CRUD ******************************** */

    void remove(Long id);

    Long save(AttributeDTO attribute);

    void edit(Long id, AttributeDTO attribute);

    Optional<AttributeDTO> get(Long id);

    List<AttributeDTO> getAll();

    Page<AttributeDTO> getAllPaginated(Pageable pageable);

    List<AttributeDTO> getAllByCategoryId(Long categoryId);

    /* ******************************** OPERATIONS ******************************** */

    boolean exists(Long id);

}