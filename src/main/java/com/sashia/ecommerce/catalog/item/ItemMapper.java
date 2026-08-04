package com.sashia.ecommerce.catalog.item;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;

import java.util.ArrayList;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getItemType(),
                item.getTitle(),
                item.getPrice(),
                item.getCategoryId(),
                new ArrayList<>()
        );
    }

}
