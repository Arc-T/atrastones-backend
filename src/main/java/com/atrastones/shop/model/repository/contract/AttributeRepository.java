package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.model.entity.Attribute;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository {

    // #################################### CREATE ####################################

    /**
     * Creates a new attribute and returns its generated ID.
     *
     * @param attribute the attribute data to create
     * @return the generated ID of the new attribute
     * @throws RuntimeException if the insert fails
     */
    Long create(AttributeDTO attribute);

    // #################################### UPDATE ####################################

    /**
     * Updates an existing attribute by ID.
     *
     * @param id        the ID of the attribute to update
     * @param attribute the updated attribute data
     * @throws EntityNotFoundException if no attribute with the given ID exists
     * @throws DataAccessException     if the update fails
     */
    void update(Long id, AttributeDTO attribute);

    // -------------------------------------- DELETE --------------------------------------

    /**
     * Deletes an attribute by ID.
     *
     * @param id the ID of the attribute to delete
     * @return true if the attribute was deleted, false if not found
     * @throws RuntimeException if the delete fails
     */
    boolean delete(Long id);

    // -------------------------------------- SELECT --------------------------------------

    /**
     * Retrieves an attribute by ID, including fetched associations.
     *
     * @param id the ID of the attribute
     * @return an Optional containing the attribute if found
     */
    Optional<Attribute> get(Long id);

    /**
     * Retrieves all attributes paginated, including fetched associations.
     *
     * @param pageable the pagination information
     * @return a Page of attributes
     */
    Page<Attribute> getAllPaginated(Pageable pageable);

    /**
     * Retrieves all attributes for a given category ID, including fetched associations.
     *
     * @param categoryId the category ID
     * @return a list of matching attributes
     */
    List<Attribute> getAllByCategoryId(Long categoryId);

    // -------------------------------------- OPERATIONS --------------------------------------

    /**
     * Counts the total number of attributes.
     *
     * @return the total count
     */
    Long count();

    /**
     * Checks if an attribute exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(long id);

}
