package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.SmsDTO;
import com.atrastones.shop.model.entity.Sms;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SmsRepository {

    // ------------------------------ CREATE ------------------------------

    /**
     * Creates a new sms and returns its generated ID.
     *
     * @param sms the sms data to create
     * @return the generated ID of the new sms
     * @throws RuntimeException if the insert fails
     */
    Long create(SmsDTO sms);

    // ------------------------------ UPDATE ------------------------------

    /**
     * Updates an existing sms by ID.
     *
     * @param id  the ID of the sns to update
     * @param sms the updated sms data
     * @throws EntityNotFoundException if no sms with the given ID exists
     * @throws RuntimeException        if the update fails
     */
    void update(Long id, SmsDTO sms);

    // ------------------------------ SELECT ------------------------------

    /**
     * Retrieves all sms paginated, including fetched associations.
     *
     * @param pageable the pagination information
     * @return a Page of sms
     */
    Page<Sms> getAllPaginated(Pageable pageable);

    // ------------------------------ OPERATION ------------------------------

    /**
     * Counts the total number of sms.
     *
     * @return the total count
     */
    long count();

    /**
     * Checks if a sms exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(Long id);

}
