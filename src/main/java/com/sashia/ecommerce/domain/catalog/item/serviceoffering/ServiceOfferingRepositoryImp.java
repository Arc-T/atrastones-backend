package com.sashia.ecommerce.domain.catalog.item.serviceoffering;

import com.sashia.ecommerce.common.util.JdbcUtils;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceCreateDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceSearchDTO;
import com.sashia.ecommerce.domain.catalog.item.serviceoffering.common.ServiceUpdateDTO;
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
public class ServiceOfferingRepositoryImp implements ServiceOfferingRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public ServiceOfferingRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ==================================== CREATE ====================================

    @Override
    public long create(ServiceCreateDTO serviceOffering) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO serviceOfferings (name, cost, service_group_id, description)
                                       VALUES (:name, :cost, :service_group_id, :description)
                                """)
                        .param("name", serviceOffering.name())
                        .param("cost", serviceOffering.cost())
                        .param("service_group_id", serviceOffering.serviceGroupId())
                        .param("description", serviceOffering.description())
        );
    }

    // ==================================== GET ====================================

    @Override
    public Optional<ServiceOffering> get(Long id) {
        return Optional.ofNullable(entityManager.createQuery("""
                        SELECT s FROM ServiceOffering s
                                 WHERE s.id = :id
                        """, ServiceOffering.class)
                .setParameter("id", id)
                .getSingleResultOrNull());
    }

    @Override
    public Page<ServiceOffering> getAll(Pageable pageable, ServiceSearchDTO search) {
        List<ServiceOffering> serviceOfferings = entityManager.createQuery(createCriteriaQuery(search))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(serviceOfferings, pageable, serviceOfferings.size());
    }

    // ==================================== UPDATE ====================================

    @Override
    public void update(Long id, ServiceUpdateDTO serviceOffering) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE serviceOfferings
                                       SET name = :name, cost = :cost, service_group_id = :service_group_id, description = :description
                                       WHERE id = :id
                                """)
                        .param("id", id)
                        .param("name", serviceOffering.name())
                        .param("cost", serviceOffering.cost())
                        .param("service_group_id", serviceOffering.serviceGroupId())
                        .param("description", serviceOffering.description())
        );
    }

    // ==================================== DELETE ====================================

    @Override
    public boolean delete(Long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM serviceOfferings WHERE id = :id")
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

    private CriteriaQuery<ServiceOffering> createCriteriaQuery(ServiceSearchDTO search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ServiceOffering> query = cb.createQuery(ServiceOffering.class);
        Root<ServiceOffering> root = query.from(ServiceOffering.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(search.name())) {
            predicates.add(cb.like(root.get("name"), "%" + search.name() + "%"));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])));
    }

}
