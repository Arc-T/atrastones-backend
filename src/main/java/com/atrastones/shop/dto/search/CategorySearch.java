package com.atrastones.shop.dto.search;

public record CategorySearch(String name, Boolean onlyParents, Boolean onlyChildren) {
}