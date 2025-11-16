package com.atrastones.shop.api.search;

import java.util.List;

public record ProductSearch(Long categoryId, List<Long> attributeIds) {
}
