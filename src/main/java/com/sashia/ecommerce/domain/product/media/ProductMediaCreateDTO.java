package com.sashia.ecommerce.domain.product.media;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record ProductMediaCreateDTO(
        @Nullable Long productId,
        MultipartFile[] media
) {
}