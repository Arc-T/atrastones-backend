package com.atrastones.shop.api.filter;

import lombok.Getter;

@Getter
public class CategoryFilter {

    String name;
    Boolean onlyParents;
    Boolean onlyChildren;

    public CategoryFilter(String name,
                          Boolean only_parents,
                          Boolean only_children) {
        this.name = name;
        this.onlyParents = only_parents;
        this.onlyChildren = only_children;
    }

}