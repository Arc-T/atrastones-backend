package com.atrastones.ecommerce.category;

import com.atrastones.ecommerce.category.common.CategoryCreateDTO;
import com.atrastones.ecommerce.category.common.CategorySearchDTO;
import com.atrastones.ecommerce.category.common.CategoryUpdateDTO;
import com.atrastones.infrastructure.db.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImp implements CategoryRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ==================================== CREATE ====================================

    @Override
    public Long save(CategoryCreateDTO category) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO categories (name, url, icon, parent_id, display_order, description)
                                       VALUES (:name, :url, :icon, :parent_id, :display_order, :description)
                                """)
                        .param("name", category.name())
                        .param("url", category.url())
                        .param("icon", category.icon())
                        .param("parent_id", category.parentId())
                        .param("display_order", category.displayOrder())
                        .param("description", category.description())
        );
    }

    // ==================================== GET ====================================

    @Override
    public Optional<Category> find(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("""
                                SELECT c FROM Category c
                                         WHERE c.id = :id
                                """, Category.class)
                        .setParameter("id", id)
                        .getSingleResultOrNull()
        );
    }

    @Override
    public Page<Category> findAll(Pageable pageable, CategorySearchDTO search) {
        List<Category> categories = entityManager.createQuery(createCriteriaQuery(search))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(categories, pageable, categories.size());
    }

    // ==================================== UPDATE ====================================

    @Override
    public void update(Long id, CategoryUpdateDTO category) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE categories
                                       SET name = :name, url = :url, icon = :icon, parent_id = :parent_id,
                                           display_order = :display_order, description = :description
                                       WHERE id = :id
                                """)
                        .param("id", id)
                        .param("name", category.name())
                        .param("url", category.url())
                        .param("icon", category.icon())
                        .param("parent_id", category.parentId())
                        .param("display_order", category.displayOrder())
                        .param("description", category.description())
        );
    }

    // ==================================== DELETE ====================================

    @Override
    public boolean delete(Long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM categories WHERE id = :id")
                        .param("id", id)
        );
    }

    // ==================================== OPERATION ====================================

    @Override
    public Long count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM categories")
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM categories WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    // ==================================== HELPERS ====================================

    private CriteriaQuery<Category> createCriteriaQuery(CategorySearchDTO search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(search.name())) {
            predicates.add(cb.like(root.get("name"), "%" + search.name() + "%"));
        }

        if (Boolean.TRUE.equals(search.onlyChildren())) {
            predicates.add(cb.isNotNull(root.get("parentId")));
        } else if (Boolean.TRUE.equals(search.onlyParents())) {
            predicates.add(cb.isNull(root.get("parentId")));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])));
    }

}