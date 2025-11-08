package com.atrastones.shop.api.filter;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductFilter {

    Long categoryId;
    List<Long> attributeIds;

    public ProductFilter(Long category_id, List<Long> attribute_ids) {
        this.categoryId = category_id;
        this.attributeIds = attribute_ids;
    }

}
