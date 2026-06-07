package com.atrastones.ecommerce.product;

public record MediaDTO(String url, String extension) {

    public MediaDTO(String url) {
        this(url, null);
    }

}