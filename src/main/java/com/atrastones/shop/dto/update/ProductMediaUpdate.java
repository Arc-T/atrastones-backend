package com.atrastones.shop.dto.update;

import org.springframework.web.multipart.MultipartFile;

public record ProductMediaUpdate(MultipartFile[] media) {
}
