package com.sashia.ecommerce.domain.product;

public record MediaDTO(String url, String extension) {

    public MediaDTO(String url) {
        this(url, null);
    }

}