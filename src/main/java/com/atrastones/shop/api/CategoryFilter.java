package com.atrastones.shop.api;

import lombok.Value;

@Value
public class CategoryFilter {

    String name;

    Boolean onlyParents;

    Boolean onlyChildren;

}
