package com.atrastones.ecommerce.attribute;

import com.atrastones.ecommerce.attribute.common.AttributeCreateDTO;
import com.atrastones.ecommerce.attribute.common.AttributeSearchDTO;
import com.atrastones.ecommerce.attribute.common.AttributeUpdateDTO;
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
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
class AttributeRepositoryImp implements AttributeRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public AttributeRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    //  =====================================  INSERT  =====================================

    @Override
    public Long save(AttributeCreateDTO attribute) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO attributes (name, category_id, type, is_filterable)
                                       VALUES (:name, :category_id, :type, :is_filterable)
                                """)
                        .param("name", attribute.name())
                        .param("category_id", attribute.categoryId())
                        .param("type", attribute.type())
                        .param("is_filterable", attribute.isFilterable())
        );
    }

    // ===================================== SELECT =====================================

    @Override
    public Optional<Attribute> findById(Long id) {
        return entityManager.createQuery("""
                        SELECT a FROM Attribute a
                                 JOIN FETCH a.category
                                 LEFT JOIN FETCH a.attributeValuesPivot
                                 WHERE a.id = :id
                        """, Attribute.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Page<Attribute> findAll(AttributeSearchDTO search, Pageable pageable) {
        List<Attribute> attributes = entityManager.createQuery(createCriteriaQuery(search))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (attributes.isEmpty())
            return PageableExecutionUtils.getPage(List.of(), pageable, () -> 0L);

        return new PageImpl<>(attributes, pageable, count());
    }

    @Override
    public List<Attribute> findAllByCategoryId(Long categoryId) {
        return entityManager.createQuery("""
                        SELECT a FROM Attribute a
                                 JOIN FETCH a.category c
                                 WHERE c.id = :category_id
                        """, Attribute.class)
                .setParameter("category_id", categoryId)
                .getResultList();
    }

    //  =====================================  UPDATE  =====================================

    @Override
    public void update(Long id, AttributeUpdateDTO attribute) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE attributes
                                       SET name = :name, category_id = :category_id,
                                           type = :type, is_filterable = :is_filterable
                                       WHERE id = :id
                                """)
                        .param("id", id)
                        .param("name", attribute.name())
                        .param("category_id", attribute.categoryId())
                        .param("type", attribute.type()) //TODO: this should be dynamic
                        .param("is_filterable", attribute.isFilterable())
        );
    }

    //  =====================================  DELETE  =====================================

    @Override
    public boolean delete(Long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM attributes WHERE id = :id")
                        .param("id", id)
        );
    }

    //  =====================================  OPERATIONS  =====================================

    @Override
    public Long count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM attributes")
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM attributes WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    //  =====================================  HELPERS  =====================================

    private CriteriaQuery<Attribute> createCriteriaQuery(AttributeSearchDTO search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attribute> query = cb.createQuery(Attribute.class);
        Root<Attribute> root = query.from(Attribute.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(search.name())) {
            predicates.add(cb.like(root.get("name"), "%" + search.name() + "%"));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])));
    }

}