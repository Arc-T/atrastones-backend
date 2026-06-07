package com.atrastones.ecommerce.product.media;

import org.springframework.web.multipart.MultipartFile;

public record ProductMediaUpdateDTO(MultipartFile[] media) {
}
