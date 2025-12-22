package com.atrastones.shop.dto.create;

import org.springframework.web.multipart.MultipartFile;

public record ProductMediaCreate(
            MultipartFile[] media
) {
}