package com.sashia.ecommerce.domain.catalog.item;

import jakarta.persistence.*;

@Entity
@Table(name = "item_type")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private ItemTypeEnum type;

}
