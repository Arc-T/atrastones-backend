package com.atrastones.shop.api.search;

public record CategorySearch(String name, Boolean onlyParents, Boolean onlyChildren) {
}