package com.sashia.ecommerce.domain.catalog.item.serviceoffering.group;

import com.sashia.ecommerce.common.util.JdbcUtils;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common.ServiceGroupCreateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common.ServiceGroupSearchDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common.ServiceGroupUpdateDTO;
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
public class ServiceGroupRepositoryImp implements ServiceGroupRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public ServiceGroupRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ==================================== CREATE ====================================

    @Override
    public long create(ServiceGroupCreateDTO service) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO service_groups (name, description)
                                       VALUES (:name, :description)
                                """)
                        .param("name", service.name())
                        .param("description", service.description())
        );
    }

    // ==================================== UPDATE ====================================

    @Override
    public void update(Long id, ServiceGroupUpdateDTO service) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE service_groups
                                       SET name = :name, description = :description
                                       WHERE id = :id
                                """)
                        .param("id", id)
                        .param("name", service.name())
                        .param("description", service.description())
        );
    }

    // ==================================== DELETE ====================================

    @Override
    public boolean delete(Long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM service_groups WHERE id = :id")
                        .param("id", id)
        );
    }

    // ==================================== SELECT ====================================

    @Override
    public Optional<ServiceGroup> get(Long id) {
        return Optional.ofNullable(entityManager.createQuery("""
                        SELECT sg FROM ServiceGroup sg
                                 WHERE sg.id = :id
                        """, ServiceGroup.class)
                .setParameter("id", id)
                .getSingleResultOrNull());
    }

    @Override
    public Page<ServiceGroup> getAll(Pageable pageable, ServiceGroupSearchDTO search) {
        List<ServiceGroup> serviceGroups = entityManager.createQuery(createCriteriaQuery(search))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(serviceGroups, pageable, serviceGroups.size());
    }

    // ==================================== OPERATIONS ====================================

    @Override
    public Long count() {
        return 0L;
    }

    @Override
    public boolean exists(Long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM service_groups WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    // ==================================== HELPERS ====================================

    private CriteriaQuery<ServiceGroup> createCriteriaQuery(ServiceGroupSearchDTO search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ServiceGroup> query = cb.createQuery(ServiceGroup.class);
        Root<ServiceGroup> root = query.from(ServiceGroup.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(search.name())) {
            predicates.add(cb.like(root.get("name"), "%" + search.name() + "%"));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])));
    }

}