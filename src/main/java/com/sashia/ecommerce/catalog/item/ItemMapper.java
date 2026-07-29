package com.sashia.ecommerce.catalog.item;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getItemType(),
                item.getTitle(),
                item.getPrice(),
                item.getCategoryId()
        );
    }

}
