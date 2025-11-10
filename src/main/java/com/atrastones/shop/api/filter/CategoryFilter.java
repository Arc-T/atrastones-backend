package com.atrastones.shop.api.filter;

import lombok.Value;

@Value
public class CategoryFilter {

    String name;
    Boolean onlyParents;
    Boolean onlyChildren;

}