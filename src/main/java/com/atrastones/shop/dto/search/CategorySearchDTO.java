package com.atrastones.shop.dto.search;

public record CategorySearchDTO(String name, Boolean onlyParents, Boolean onlyChildren) {
}