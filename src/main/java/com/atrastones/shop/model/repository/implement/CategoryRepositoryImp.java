package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.search.CategorySearchDTO;
import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.model.entity.Category;
import com.atrastones.shop.model.repository.contract.CategoryRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class CategoryRepositoryImp implements CategoryRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
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
                        .param("name", category.name())
                        .param("url", category.url())
                        .param("icon", category.icon())
                        .param("parent_id", category.parentId())
                        .param("display_order", category.displayOrder())
                        .param("description", category.description())
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
                        .param("name", category.name())
                        .param("url", category.url())
                        .param("icon", category.icon())
                        .param("parent_id", category.parentId())
                        .param("display_order", category.displayOrder())
                        .param("description", category.description())
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
    public Page<Category> getAll(Pageable pageable, CategorySearchDTO search) {

        List<Category> categories = buildQueryWithFilters(search)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(categories, pageable, categories.size());
    }

    // -------------------------------------- OPERATION --------------------------------------

    @Override
    public long count() {

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

    // -------------------------------------- HELPERS --------------------------------------

    private TypedQuery<Category> buildQueryWithFilters(CategorySearchDTO filter) {

        StringBuilder hql = new StringBuilder("SELECT c FROM Category c");

        if (filter.onlyChildren() != null && filter.onlyParents() != null && filter.name() != null) {

            hql.append(" WHERE 1=1");

            if (filter.onlyChildren())
                hql.append(" AND c.parentId IS NOT NULL");

            else if (filter.onlyParents())
                hql.append(" AND c.parentId IS NULL");

            if (StringUtils.hasText(filter.name()))
                hql.append(" AND LOWER(c.name) LIKE LOWER(:name)");
        }

        TypedQuery<Category> query = entityManager.createQuery(hql.toString(), Category.class);

        if (StringUtils.hasText(filter.name()))
            query.setParameter("name", "%" + filter.name().trim() + "%");

        return query;
    }

}