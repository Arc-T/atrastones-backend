package com.sashia.ecommerce.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//    Page<Category> findAll(Pageable pageable, CategorySearchDTO search);

}