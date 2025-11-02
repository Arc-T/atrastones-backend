package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.entity.Tag;
import com.atrastones.shop.model.repository.contract.TagRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link TagRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for tags. Uses {@link JdbcClient} for simple operations (create, update, delete, exists)
 * for performance, and {@link EntityManager} for select queries to leverage ORM benefits.
 */
@Repository
public class TagRepositoryImp implements TagRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public TagRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // ------------------------------------------------- CREATE -------------------------------------------------

    @Override
    public Long create(TagDTO tag) {

        String INSERT_TAG_SQL = """
                INSERT INTO tags (name)
                       VALUES (:name)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_TAG_SQL)
                        .param("name", tag.getName())
        );
    }

    // ------------------------------------------------- UPDATE -------------------------------------------------

    @Override
    public void update(Long id, TagDTO tag) {

        String UPDATE_TAG_SQL = """
                UPDATE tags
                       SET name = :name
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_TAG_SQL)
                        .param("name", tag.getName())
                        .param("id", id)
                , "TAG.ID.INVALID"
        );
    }

    // ------------------------------------------------- DELETE -------------------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_TAG_SQL = """
                DELETE FROM tags WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_TAG_SQL)
                        .param("id", id)
                , "TAG.ID.INVALID"
        );
    }

    // ------------------------------------------------- SELECT -------------------------------------------------

    @Override
    public Page<Tag> getAllPaginated(Pageable pageable) {

        String SELECT_ALL_TAG_HQL = """
                SELECT t FROM Tag t
                """;

        return PageableExecutionUtils.getPage(
                entityManager.createQuery(SELECT_ALL_TAG_HQL, Tag.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    // ------------------------------------------------- OPERATION -------------------------------------------------

    @Override
    public long count() {

        String COUNT_TAG_SQL = """
                SELECT COUNT(*) FROM tags
                """;

        return jdbcClient.sql(COUNT_TAG_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {

        String EXISTS_TAG_SQL = """
                SELECT EXISTS(SELECT 1 FROM tags WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_TAG_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

}
