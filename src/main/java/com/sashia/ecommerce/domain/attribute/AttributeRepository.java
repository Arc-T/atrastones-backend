package com.sashia.ecommerce.domain.attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long>, JpaSpecificationExecutor<Attribute> {

    @Query("""
              SELECT a FROM Attribute a
                      JOIN FETCH a.attributeValues
                      JOIN FETCH a.category
              WHERE a.id = :id
            """)
    Optional<Attribute> findByIdWithFullDetails(Long id);

}