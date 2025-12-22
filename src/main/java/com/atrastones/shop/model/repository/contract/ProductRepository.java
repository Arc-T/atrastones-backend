package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.create.ProductCreate;
import com.atrastones.shop.dto.search.ProductSearch;
import com.atrastones.shop.dto.ProductDTO;
import com.atrastones.shop.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // ---------------------------- CREATE ----------------------------

    Long create(ProductCreate product);

    // ---------------------------- UPDATE ----------------------------

    void update(ProductDTO product);

    // ---------------------------- DELETE ----------------------------

    boolean delete(long id);

    // ---------------------------- SELECT ----------------------------

    Optional<Product> get(long id);

    Optional<Product> getDetails(long id);

    Page<Product> getAllPaginated(Pageable pageable, ProductSearch filter);

    List<Product> getAllByCategoryId(long categoryId);

    List<Product> getOrderItems(long orderId);

    // ---------------------------- OPERATIONS ----------------------------

    long count();

    boolean exists(long id);

    void addProductStats(long userId, long productId);

}