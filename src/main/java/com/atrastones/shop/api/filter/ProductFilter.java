package com.atrastones.shop.api.filter;

import lombok.Value;

import java.util.List;

@Value
public class ProductFilter {

    Long categoryId;
    List<Long> attributeIds;

}
