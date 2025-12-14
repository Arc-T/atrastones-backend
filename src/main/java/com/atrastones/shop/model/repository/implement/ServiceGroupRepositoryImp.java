package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.api.search.ServiceGroupSearch;
import com.atrastones.shop.dto.ServiceGroupDTO;
import com.atrastones.shop.model.entity.ServiceGroup;
import com.atrastones.shop.model.repository.contract.ServiceGroupRepository;
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
public class ServiceGroupRepositoryImp implements ServiceGroupRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public ServiceGroupRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // -------------------------------------- CREATE --------------------------------------

    @Override
    public long create(ServiceGroupDTO service) {

        String INSERT_SERVICE_GROUP_SQL = """
                INSERT INTO service_groups (name, description)
                       VALUES (:name, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_SERVICE_GROUP_SQL)
                        .param("name", service.name())
                        .param("description", service.description())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(long id, ServiceGroupDTO service) {

        String UPDATE_SERVICE_GROUP_SQL = """
                UPDATE service_groups
                       SET name = :name, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_SERVICE_GROUP_SQL)
                        .param("id", id)
                        .param("name", service.name())
                        .param("description", service.description())
                , "SERVICE-GROUP.ID.INVALID"
        );
    }

    // -------------------------------------- DELETE --------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_SERVICE_GROUP_SQL = """
                DELETE FROM service_groups WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_SERVICE_GROUP_SQL)
                        .param("id", id),
                "SERVICE-GROUP.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public Optional<ServiceGroup> get(Long id) {

        String SELECT_SERVICE_HQL = """
                SELECT sg FROM ServiceGroup sg
                         WHERE sg.id = :id
                """;

        return entityManager.createQuery(SELECT_SERVICE_HQL, ServiceGroup.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<ServiceGroup> getAll(ServiceGroupSearch search) {
        return buildQueryWithFilters(search).getResultList();
    }

    @Override
    public Page<ServiceGroup> getAllPaginated(Pageable pageable, ServiceGroupSearch search) {

        List<ServiceGroup> serviceGroups = buildQueryWithFilters(search)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(serviceGroups, pageable, serviceGroups.size());
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

    private TypedQuery<ServiceGroup> buildQueryWithFilters(ServiceGroupSearch search) {

        StringBuilder hql = new StringBuilder();
        hql.append("SELECT sg FROM ServiceGroup sg");

        return entityManager.createQuery(hql.toString(), ServiceGroup.class);
    }

}
