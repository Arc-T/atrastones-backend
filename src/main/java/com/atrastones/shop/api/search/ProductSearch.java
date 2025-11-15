package com.atrastones.shop.api.search;

import lombok.Value;

import java.util.List;

@Value
public class ProductSearch {

    Long categoryId;
    List<Long> attributeIds;

}
