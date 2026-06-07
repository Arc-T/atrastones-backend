package com.atrastones.ecommerce.tag;

import com.atrastones.ecommerce.tag.common.TagCreateDTO;
import com.atrastones.ecommerce.tag.common.TagSearchDTO;
import com.atrastones.ecommerce.tag.common.TagUpdateDTO;
import com.atrastones.infrastructure.db.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImp implements TagRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public TagRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // =============================== CREATE ===============================

    @Override
    public Long create(TagCreateDTO tag) {
        return JdbcUtils.insert(
                jdbcClient.sql("INSERT INTO tags (name) VALUES (:name)")
                        .param("name", tag.name())
        );
    }

    // =============================== UPDATE ===============================

    @Override
    public void update(Long id, TagUpdateDTO tag) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE tags
                                       SET name = :name
                                       WHERE id = :id
                                """)
                        .param("name", tag.name())
                        .param("id", id)
        );
    }

    // =============================== DELETE ===============================

    @Override
    public boolean delete(Long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM tags WHERE id = :id")
                        .param("id", id)
        );
    }

    // =============================== SELECT ===============================

    @Override
    public Optional<Tag> get(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("SELECT t FROM Tag t WHERE t.id = :id", Tag.class)
                        .setParameter("id", id)
                        .getSingleResultOrNull()
        );
    }

    @Override
    public Page<Tag> getAll(TagSearchDTO search, Pageable pageable) {
        return PageableExecutionUtils.getPage(
                entityManager.createQuery(createCriteriaQuery(search))
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    // =============================== OPERATION ===============================

    @Override
    public long count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM tags")
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM tags WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    // =============================== HELPERS ===============================

    private CriteriaQuery<Tag> createCriteriaQuery(TagSearchDTO search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = cb.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(search.name())) {
            predicates.add(cb.like(root.get("name"), "%" + search.name() + "%"));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])));
    }

}
