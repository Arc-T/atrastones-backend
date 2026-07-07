package com.sashia.ecommerce.domain.product.media;

import org.springframework.web.multipart.MultipartFile;

public record ProductMediaUpdateDTO(MultipartFile[] media) {
}
