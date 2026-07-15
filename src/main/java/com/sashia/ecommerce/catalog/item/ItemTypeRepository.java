package com.sashia.ecommerce.catalog.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

    Optional<ItemType> findByCatalogItemType(CatalogItemType catalogItemType);

}
