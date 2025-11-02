package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.ProductDTO;
import com.atrastones.shop.model.entity.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // ---------------------------- CREATE ----------------------------

    /**
     * Creates a new product and returns its generated ID.
     *
     * @param product the product data to create
     * @return the generated ID of the new product
     * @throws RuntimeException if the insert fails
     */
    Long create(ProductDTO product);

    /**
     * Retrieves a product by ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the product if found
     */
    Optional<Product> get(long id);

    // ---------------------------- UPDATE ----------------------------

    /**
     * Updates an existing product by ID.
     *
     * @param product the updated product data
     * @throws EntityNotFoundException if no product with the given ID exists
     * @throws RuntimeException        if the update fails
     */
    void update(ProductDTO product);

    // ---------------------------- DELETE ----------------------------

    /**
     * Deletes a product by ID.
     *
     * @param id the ID of the product to delete
     * @return the ID of the deleted product
     * @throws EntityNotFoundException if no product with the given ID exists
     * @throws RuntimeException        if the delete fails
     */
    boolean delete(long id);

    // ---------------------------- SELECT ----------------------------

    /**
     * Retrieves detailed product information by ID, including associated entities.
     *
     * @param id the ID of the product
     * @return an Optional containing the product if found
     */
    Optional<Product> getDetails(long id);

    /**
     * Retrieves all products paginated, including associated entities.
     *
     * @param pageable the pagination information
     * @return a Page of products
     */
    Page<Product> getAllPaginated(Pageable pageable);

    /**
     * Retrieves all products for a given category ID.
     *
     * @param categoryId the category ID
     * @return a list of matching products
     */
    List<Product> getAllByCategoryId(long categoryId);

    /**
     * Retrieves all products for a given order ID.
     *
     * @param orderId the order ID
     * @return a list of matching products
     */
    List<Product> getOrderItems(long orderId);

    // ---------------------------- OPERATIONS ----------------------------

    /**
     * Counts the total number of products.
     *
     * @return the total count
     */
    long count();

    /**
     * Checks if a product exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(long id);

    /**
     * Adds product statistics for a given user and product.
     *
     * @param userId    the ID of the user
     * @param productId the ID of the product
     * @throws EntityNotFoundException if the product or user does not exist
     * @throws RuntimeException        if the operation fails
     */
    void addProductStats(long userId, long productId);

}