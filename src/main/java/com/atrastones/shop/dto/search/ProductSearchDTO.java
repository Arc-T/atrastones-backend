package com.atrastones.shop.dto.search;

import java.util.List;

public record ProductSearchDTO(Long categoryId, List<Long> attributeIds) {
}
