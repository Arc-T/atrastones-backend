package com.atrastones.shop.api.search;

import lombok.Value;

@Value
public class CategorySearch {

    String name;
    Boolean onlyParents;
    Boolean onlyChildren;

}