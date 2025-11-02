package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.model.entity.Attribute;
import com.atrastones.shop.model.repository.contract.AttributeRepository;
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
 * Implementation of {@link AttributeRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for attributes. Uses {@link JdbcClient} for simple operations (create, update, delete, count, exists)
 * for performance, and {@link EntityManager} for complex queries with joins to leverage ORM benefits.
 * </p>
 */
@Repository
public class AttributeRepositoryImp implements AttributeRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public AttributeRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    //  --------------------------------------  CREATE  --------------------------------------

    @Override
    public Long create(AttributeDTO attribute) {

        String INSERT_ATTRIBUTE_SQL = """
                INSERT INTO attributes (name, category_id, type, is_filterable)
                       VALUES (:name, :category_id, :type, :is_filterable)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_ATTRIBUTE_SQL)
                        .param("name", attribute.getName())
                        .param("category_id", attribute.getCategoryId())
                        .param("type", "TEXT")
                        .param("is_filterable", attribute.getIsFilterable())
        );
    }

    //  --------------------------------------  UPDATE  --------------------------------------

    @Override
    public void update(Long id, AttributeDTO attribute) {

        String UPDATE_ATTRIBUTE_SQL = """
                UPDATE attributes
                       SET name = :name, category_id = :category_id, type = :type, is_filterable = :is_filterable
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_ATTRIBUTE_SQL)
                        .param("id", id)
                        .param("name", attribute.getName())
                        .param("category_id", attribute.getCategoryId())
                        .param("type", attribute.getType())
                        .param("is_filterable", attribute.getIsFilterable()),
                "ATTRIBUTE.ID.INVALID"
        );
    }

    //  --------------------------------------  DELETE  --------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_ATTRIBUTE_SQL = """
                DELETE FROM attributes WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_ATTRIBUTE_SQL)
                        .param("id", id),
                "ATTRIBUTE.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public Optional<Attribute> get(Long id) {

        String SELECT_ATTRIBUTE_HQL = """
                SELECT a FROM Attribute a
                         JOIN FETCH a.category
                         JOIN FETCH a.attributeValuesMap
                         WHERE a.id = :id
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_ATTRIBUTE_HQL, Attribute.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

    @Override
    public Page<Attribute> getAllPaginated(Pageable pageable) {

        String SELECT_ATTRIBUTE_IDS_HQL = """
                SELECT a.id FROM Attribute a
                """;

        List<Long> attributeIds = entityManager.createQuery(SELECT_ATTRIBUTE_IDS_HQL, Long.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (attributeIds.isEmpty())
            return PageableExecutionUtils.getPage(List.of(), pageable, () -> 0L);

        String SELECT_ATTRIBUTE_VALUES = """
                SELECT a FROM Attribute a
                         JOIN FETCH a.category
                         LEFT JOIN FETCH a.attributeValuesPivot avp
                         LEFT JOIN FETCH avp.attributeValue
                         WHERE a.id IN :attributeIds
                """;

        List<Attribute> withValues = entityManager.createQuery(SELECT_ATTRIBUTE_VALUES, Attribute.class)
                .setParameter("attributeIds", attributeIds)
                .getResultList();

        return PageableExecutionUtils.getPage(
                withValues,
                pageable,
                this::count
        );
    }

    @Override
    public List<Attribute> getAllByCategoryId(Long categoryId) {

        String SELECT_ATTRIBUTES_BY_CATEGORY_HQL = """
                SELECT a FROM Attribute a
                         JOIN FETCH a.category c
                         WHERE c.id = :category_id
                """;

        return entityManager.createQuery(SELECT_ATTRIBUTES_BY_CATEGORY_HQL, Attribute.class)
                .setParameter("category_id", categoryId)
                .getResultList();
    }

    //  --------------------------------------  OPERATIONS  --------------------------------------

    @Override
    public Long count() {

        String COUNT_ATTRIBUTE_SQL = """
                SELECT COUNT(*) FROM attributes
                """;

        return jdbcClient.sql(COUNT_ATTRIBUTE_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(long id) {

        String EXISTS_ATTRIBUTE_SQL = """
                SELECT EXISTS(SELECT 1 FROM attributes WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_ATTRIBUTE_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

}