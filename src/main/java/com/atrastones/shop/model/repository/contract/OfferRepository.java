package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.OfferDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;

public interface OfferRepository {

    // ----------------------------- CREATE --------------------------------------

    /**
     * Creates a new offer and returns its generated ID.
     *
     * @param offer the offer data to create
     * @return the generated ID of the new offer
     * @throws RuntimeException if the insert fails
     */
    long create(OfferDTO offer);

    // ----------------------------- UPDATE --------------------------------------

    /**
     * Updates an existing offer by ID.
     *
     * @param id    the ID of the offer to update
     * @param offer the updated offer data
     * @throws EntityNotFoundException if no offer with the given ID exists
     * @throws DataAccessException        if the update fails
     */
    void update(long id, OfferDTO offer);

    // ----------------------------- SELECT --------------------------------------


    // ----------------------------- OPERATIONS --------------------------------------

    /**
     * Counts the total number of offers.
     *
     * @return the total count
     */
    Long count();

    /**
     * Checks if an offer exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(Long id);

}
