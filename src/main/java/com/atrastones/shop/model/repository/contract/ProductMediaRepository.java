package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.ProductMediaDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ProductMediaRepository {

    // ---------------------------------- CREATE ----------------------------------

    /**
     * Inserts a new product media record into the database and returns its generated ID.
     *
     * @param productMedia the product media data to insert; must not be null
     * @return the generated ID of the newly created media record
     * @throws RuntimeException if the insert fails
     */
    List<Long> createBatch(List<ProductMediaDTO> productMedia);

    // ---------------------------------- UPDATE ----------------------------------

    /**
     * Updates an existing productMedia by ID.
     *
     * @param id           the ID of the productMedia to update
     * @param productMedia the updated productMedia data
     * @throws EntityNotFoundException if no productMedia with the given ID exists
     * @throws RuntimeException        if the update fails
     */
    void update(Long id, ProductMediaDTO productMedia);

    // ---------------------------------- DELETE ----------------------------------

    /**
     * Deletes a productMedia by ID.
     *
     * @param id the ID of the productMedia to delete
     * @return true if the productMedia was deleted, false if not found
     * @throws RuntimeException if the delete fails
     */
    boolean delete(Long id);

    // ---------------------------------- SELECT ----------------------------------

}
