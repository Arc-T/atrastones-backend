package com.atrastones.shop.dto.search;

import java.util.List;

public record ProductSearch(Long categoryId, List<Long> attributeIds) {
}
