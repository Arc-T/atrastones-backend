package com.atrastones.ecommerce.product.media;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record ProductMediaCreateDTO(
        @Nullable Long productId,
        MultipartFile[] media
) {
}