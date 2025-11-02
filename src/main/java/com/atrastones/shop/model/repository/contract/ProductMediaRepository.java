package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.ProductMediaDTO;
import jakarta.persistence.EntityNotFoundException;

public interface ProductMediaRepository {

    // ---------------------------------- CREATE ----------------------------------

    /**
     * Creates a new media and returns its generated ID.
     *
     * @param productMedia the productMedia data to create
     * @return the generated ID of the new media
     * @throws RuntimeException if the insert fails
     */
    Long create(ProductMediaDTO productMedia);

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
