package com.atrastones.shop.api.create;

import org.springframework.web.multipart.MultipartFile;

public record ProductMediaCreate(
        MultipartFile[] media
) {
}
