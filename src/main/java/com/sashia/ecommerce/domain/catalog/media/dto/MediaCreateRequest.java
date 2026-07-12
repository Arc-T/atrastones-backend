package com.sashia.ecommerce.domain.catalog.media.dto;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record MediaCreateRequest(
        @Nullable Long productId,
        MultipartFile[] media
) {
}