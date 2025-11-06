package com.atrastones.shop.api.filter;

public record CategoryFilter(String name, Boolean onlyParents, Boolean onlyChildren) {
}