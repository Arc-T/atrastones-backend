package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.api.search.CategorySearch;
import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.model.entity.Category;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    // -------------------------------------- CREATE --------------------------------------

    /**
     * Creates a new category and returns its generated ID.
     *
     * @param category the category data to create
     * @return the generated ID of the new category
     * @throws RuntimeException if the insert fails
     */
    Long create(CategoryDTO category);

    // -------------------------------------- UPDATE --------------------------------------

    /**
     * Updates an existing category by ID.
     *
     * @param id       the ID of the category to update
     * @param category the updated category data
     * @throws EntityNotFoundException if no category with the given ID exists
     * @throws RuntimeException        if the update fails
     */
    void update(Long id, CategoryDTO category);

    // -------------------------------------- DELETE --------------------------------------

    /**
     * Deletes a category by ID.
     *
     * @param id the ID of the category to delete
     * @return true if deleted, else false
     * @throws EntityNotFoundException if no category with the given ID exists
     * @throws RuntimeException        if the delete fails
     */
    boolean delete(Long id);

    // -------------------------------------- SELECT --------------------------------------

    /**
     * Retrieves a category by ID.
     *
     * @param id the ID of the category
     * @return an Optional containing the category if found
     */
    Optional<Category> get(Long id);

    /**
     * Retrieves all categories, including fetched associations.
     *
     * @param search key-value params for filtering categories
     * @return a List of categories
     */
    List<Category> getAll(CategorySearch search);

    /**
     * Retrieves all categories paginated, including fetched associations.
     *
     * @param search key-value params for filtering categories
     * @param pageable   the pagination information
     * @return a Page of categories
     */
    Page<Category> getAllPaginated(Pageable pageable, CategorySearch search);

    // -------------------------------------- OPERATIONS --------------------------------------

    /**
     * Counts the total number of categories.
     *
     * @return the total count
     */
    Long count();

    /**
     * Checks if a category exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(Long id);

}