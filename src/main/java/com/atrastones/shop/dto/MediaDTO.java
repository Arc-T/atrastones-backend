package com.atrastones.shop.dto;

public record MediaDTO(String url, String extension) {

    public MediaDTO(String url) {
        this(url, null);
    }

}