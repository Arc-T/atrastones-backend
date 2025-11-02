package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.PaymentDTO;
import com.atrastones.shop.model.entity.Payment;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public interface PaymentRepository {

    // -------------------------------- CREATE --------------------------------------

    /**
     * Creates a new payment and returns its generated ID.
     *
     * @param payment the payment data to create
     * @return the generated ID of the new payment
     * @throws RuntimeException if the insert fails
     */
    Long create(PaymentDTO payment);

    // -------------------------------- UPDATE --------------------------------------

    /**
     * Updates an existing payment by ID.
     *
     * @param id      the ID of the attribute to update
     * @param payment the updated payment data
     * @throws EntityNotFoundException if no payment with the given ID exists
     * @throws RuntimeException        if the update fails
     */
    void update(Long id, PaymentDTO payment);

    // -------------------------------- SELECT --------------------------------------

    /**
     * Retrieves a payment by ID, including fetched associations.
     *
     * @param id the ID of the payment
     * @return an Optional containing the payment if found
     */
    Optional<Payment> get(Long id);

    // -------------------------------- OPERATIONS ---------------------------------

    /**
     * Counts the total number of payments.
     *
     * @return the total count
     */
    Long count();

    /**
     * Checks if a payment exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(Long id);

}
