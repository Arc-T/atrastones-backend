package com.sashia.ecommerce.domain.product;

import com.sashia.ecommerce.domain.product.common.ProductCreateDTO;
import com.sashia.ecommerce.domain.product.common.ProductProjection;
import com.sashia.ecommerce.domain.product.common.ProductSearchDTO;
import com.sashia.ecommerce.domain.product.common.ProductUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // ---------------------------- CREATE ----------------------------

    Long create(ProductCreateDTO product);

    // ---------------------------- UPDATE ----------------------------

    void update(Long id, ProductUpdateDTO product);

    // ---------------------------- DELETE ----------------------------

    boolean delete(long id);

    // ---------------------------- SELECT ----------------------------

    Optional<Product> get(long id);

    Optional<Product> getDetails(long id);

    List<Product> getAllByCategoryId(long categoryId);

    List<Product> getOrderItems(long orderId);

    Page<Product> getAllBriefInfo(Pageable pageable, ProductSearchDTO filter);

    Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO filter);

    // ---------------------------- OPERATIONS ----------------------------

    long count();

    boolean exists(long id);

    void addProductStats(long userId, long productId);

}