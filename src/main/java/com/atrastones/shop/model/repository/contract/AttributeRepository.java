package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.search.AttributeSearchDTO;
import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.model.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository {

    // #################################### CREATE ####################################

    Long create(AttributeDTO attribute);

    // #################################### UPDATE ####################################

    void update(Long id, AttributeDTO attribute);

    // -------------------------------------- DELETE --------------------------------------

    boolean delete(Long id);

    // -------------------------------------- SELECT --------------------------------------

    Optional<Attribute> get(Long id);

    Page<Attribute> getAll(AttributeSearchDTO search, Pageable pageable);

    List<Attribute> getAllByCategoryId(Long categoryId);

    // -------------------------------------- OPERATIONS --------------------------------------

    Long count();

    boolean exists(long id);

}
