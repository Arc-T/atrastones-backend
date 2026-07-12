package com.sashia.ecommerce.domain.catalog.media.dto;

import org.springframework.web.multipart.MultipartFile;

public record MediaUpdateRequest(MultipartFile[] media) {
}
