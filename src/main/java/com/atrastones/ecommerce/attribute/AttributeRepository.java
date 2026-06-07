package com.atrastones.ecommerce.attribute;

import com.atrastones.ecommerce.attribute.common.AttributeCreateDTO;
import com.atrastones.ecommerce.attribute.common.AttributeSearchDTO;
import com.atrastones.ecommerce.attribute.common.AttributeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

interface AttributeRepository {

    // #################################### INSERT ####################################

    Long save(AttributeCreateDTO attribute);

    // #################################### SELECT ####################################

    Optional<Attribute> findById(Long id);

    Page<Attribute> findAll(AttributeSearchDTO search, Pageable pageable);

    List<Attribute> findAllByCategoryId(Long categoryId);

    // #################################### UPDATE ####################################

    void update(Long id, AttributeUpdateDTO attribute);

    // #################################### DELETE ####################################

    boolean delete(Long id);

    // #################################### OPERATIONS ####################################

    Long count();

    boolean exists(long id);

}
