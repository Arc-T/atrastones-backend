package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.entity.Tag;
import com.atrastones.shop.model.repository.contract.TagRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagRepositoryImp implements TagRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public TagRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
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
                        .param("name", tag.name())
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
                        .param("name", tag.name())
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
    public Optional<Tag> get(Long id) {

        String SELECT_TAG_HQL = """
                SELECT t FROM Tag t
                         WHERE t.id = :id
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_TAG_HQL, Tag.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Page<Tag> getAll(Pageable pageable) {

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
