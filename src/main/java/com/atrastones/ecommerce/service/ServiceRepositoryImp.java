package com.atrastones.ecommerce.service;

import com.atrastones.ecommerce.service.common.ServiceCreateDTO;
import com.atrastones.ecommerce.service.common.ServiceSearchDTO;
import com.atrastones.ecommerce.service.common.ServiceUpdateDTO;
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
public class ServiceRepositoryImp implements ServiceRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public ServiceRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ==================================== CREATE ====================================

    @Override
    public long create(ServiceCreateDTO service) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO services (name, cost, service_group_id, description)
                                       VALUES (:name, :cost, :service_group_id, :description)
                                """)
                        .param("name", service.name())
                        .param("cost", service.cost())
                        .param("service_group_id", service.serviceGroupId())
                        .param("description", service.description())
        );
    }

    // ==================================== GET ====================================

    @Override
    public Optional<Service> get(Long id) {
        return Optional.ofNullable(entityManager.createQuery("""
                        SELECT s FROM Service s
                                 WHERE s.id = :id
                        """, Service.class)
                .setParameter("id", id)
                .getSingleResultOrNull());
    }

    @Override
    public Page<Service> getAll(Pageable pageable, ServiceSearchDTO search) {
        List<Service> services = entityManager.createQuery(createCriteriaQuery(search))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(services, pageable, services.size());
    }

    // ==================================== UPDATE ====================================

    @Override
    public void update(Long id, ServiceUpdateDTO service) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE services
                                       SET name = :name, cost = :cost, service_group_id = :service_group_id, description = :description
                                       WHERE id = :id
                                """)
                        .param("id", id)
                        .param("name", service.name())
                        .param("cost", service.cost())
                        .param("service_group_id", service.serviceGroupId())
                        .param("description", service.description())
        );
    }

    // ==================================== DELETE ====================================

    @Override
    public boolean delete(Long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM services WHERE id = :id")
                        .param("id", id)
        );
    }

    // ==================================== OPERATIONS ====================================

    @Override
    public Long count() {
        return 0L;
    }

    @Override
    public boolean exists(Long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM Services WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    // ==================================== HELPERS ====================================

    private CriteriaQuery<Service> createCriteriaQuery(ServiceSearchDTO search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Service> query = cb.createQuery(Service.class);
        Root<Service> root = query.from(Service.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(search.name())) {
            predicates.add(cb.like(root.get("name"), "%" + search.name() + "%"));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])));
    }

}
