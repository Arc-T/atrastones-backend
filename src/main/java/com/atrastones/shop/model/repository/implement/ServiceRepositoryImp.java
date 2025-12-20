package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.api.search.ServiceSearch;
import com.atrastones.shop.dto.ServiceDTO;
import com.atrastones.shop.model.entity.Service;
import com.atrastones.shop.model.repository.contract.ServiceRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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

    // -------------------------------------- CREATE --------------------------------------

    @Override
    public long create(ServiceDTO service) {

        String INSERT_SERVICE_SQL = """
                INSERT INTO services (name, cost, service_group_id, description)
                       VALUES (:name, :cost, :service_group_id, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_SERVICE_SQL)
                        .param("name", service.name())
                        .param("cost", service.cost())
                        .param("service_group_id", service.serviceGroupId())
                        .param("description", service.description())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(long id, ServiceDTO service) {

        String UPDATE_SERVICE_SQL = """
                UPDATE services
                       SET name = :name, cost = :cost, service_group_id = :service_group_id, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_SERVICE_SQL)
                        .param("id", id)
                        .param("name", service.name())
                        .param("cost", service.cost())
                        .param("service_group_id", service.serviceGroupId())
                        .param("description", service.description())
                , "CATEGORY.ID.INVALID"
        );
    }

    // -------------------------------------- DELETE --------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_SERVICE_SQL = """
                DELETE FROM services WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_SERVICE_SQL)
                        .param("id", id),
                "DELETE.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public Optional<Service> get(Long id) {

        String SELECT_SERVICE_HQL = """
                SELECT s FROM Service s
                         WHERE s.id = :id
                """;

        return entityManager.createQuery(SELECT_SERVICE_HQL, Service.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Service> getAll(ServiceSearch search) {
        return buildQueryWithFilters(search).getResultList();
    }

    @Override
    public Page<Service> getAllPaginated(Pageable pageable, ServiceSearch search) {

        List<Service> services = buildQueryWithFilters(search)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(services, pageable, services.size());
    }

    // -------------------------------------- OPERATIONS --------------------------------------

    @Override
    public Long count() {
        return 0L;
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

    // -------------------------------------- HELPERS --------------------------------------

    private TypedQuery<Service> buildQueryWithFilters(ServiceSearch search) {

        StringBuilder hql = new StringBuilder();
        hql.append("SELECT s FROM Service s");

        TypedQuery<Service> query = entityManager.createQuery(hql.toString(), Service.class);

        return query;
    }

}
