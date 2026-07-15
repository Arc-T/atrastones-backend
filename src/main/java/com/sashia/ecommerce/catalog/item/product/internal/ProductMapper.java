package com.sashia.ecommerce.catalog.item.product.internal;

import com.sashia.ecommerce.catalog.category.Category;
import com.sashia.ecommerce.catalog.item.product.Product;
import com.sashia.ecommerce.catalog.item.product.dto.ProductCreateRequest;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(ProductCreateRequest request, Category category) {
        Product product = new Product();
//        product.setTitle(request.name());
//        product.setSlug(request.name());
//        product.setPublished(false); //TODO: should be handled later
//        product.setFeatured(false); //TODO: should be handled later
        product.setDescription(request.description());

//        product.setCategory(category);

        return product;
    }

}
