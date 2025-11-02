package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.model.entity.Category;
import com.atrastones.shop.model.repository.contract.CategoryRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link CategoryRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for categories. Uses {@link JdbcClient} for simple operations (create, update, delete, exists)
 * for performance, and {@link EntityManager} for select queries to leverage ORM benefits.
 */
@Repository
public class CategoryRepositoryImp implements CategoryRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public CategoryRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // -------------------------------------- CREATE --------------------------------------

    @Override
    public Long create(CategoryDTO category) {

        String INSERT_CATEGORY_SQL = """
                INSERT INTO categories (name, url, icon, parent_id, display_order, description)
                VALUES (:name, :url, :icon, :parent_id, :display_order, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_CATEGORY_SQL)
                        .param("name", category.getName())
                        .param("url", category.getUrl())
                        .param("icon", category.getIcon())
                        .param("parent_id", category.getParentId())
                        .param("display_order", category.getDisplayOrder())
                        .param("description", category.getDescription())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(Long id, CategoryDTO category) {

        String UPDATE_CATEGORY_SQL = """
                UPDATE categories
                       SET name = :name, url = :url, icon = :icon, parent_id = :parent_id, display_order = :display_order, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_CATEGORY_SQL)
                        .param("id", id)
                        .param("name", category.getName())
                        .param("url", category.getUrl())
                        .param("icon", category.getIcon())
                        .param("parent_id", category.getParentId())
                        .param("display_order", category.getDisplayOrder())
                        .param("description", category.getDescription())
                , "CATEGORY.ID.INVALID"
        );
    }

    // -------------------------------------- DELETE --------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_CATEGORY_SQL = """
                DELETE FROM categories WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_CATEGORY_SQL)
                        .param("id", id)
                , "CATEGORY.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public Optional<Category> get(Long id) {

        String SELECT_CATEGORY_HQL = """
                SELECT c FROM Category c
                         WHERE c.id = :id
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_CATEGORY_HQL, Category.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public List<Category> getAll() {

        String SELECT_CATEGORY_HQL = """
                SELECT c FROM Category c
                """;

        return entityManager.createQuery(SELECT_CATEGORY_HQL, Category.class).getResultList();
    }

    @Override
    public Page<Category> getAllPaginated(Pageable pageable) {

        String SELECT_CATEGORIES_HQL = """
                SELECT c FROM Category c
                """;

        return PageableExecutionUtils.getPage(
                entityManager.createQuery(SELECT_CATEGORIES_HQL, Category.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );

    }

    // -------------------------------------- OPERATION --------------------------------------

    @Override
    public Long count() {

        String COUNT_CATEGORY_SQL = """
                SELECT COUNT(*) FROM categories
                """;

        return jdbcClient.sql(COUNT_CATEGORY_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {

        String EXISTS_CATEGORY_SQL = """
                SELECT EXISTS(SELECT 1 FROM categories WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_CATEGORY_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

}