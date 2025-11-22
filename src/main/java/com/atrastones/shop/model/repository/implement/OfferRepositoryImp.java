package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.api.search.OfferSearch;
import com.atrastones.shop.dto.OfferDTO;
import com.atrastones.shop.model.entity.Offer;
import com.atrastones.shop.model.repository.contract.OfferRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OfferRepositoryImp implements OfferRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public OfferRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // -------------------------------------- CREATE --------------------------------------

    @Override
    public long create(OfferDTO offer) {

        String INSERT_ORDER_SQL = """
                INSERT INTO offers ("name", "cost", "offer_group_id", "description")
                       VALUES (:name, :cost, :offer_group_id, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_ORDER_SQL)
                        .param("name", offer.getName())
                        .param("cost", offer.getCost())
                        .param("offer_group_id", offer.getOfferGroupId())
                        .param("description", offer.getDescription())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(long id, OfferDTO offer) {

        String UPDATE_OFFER_SQL = """
                UPDATE offers
                       SET name = :name, cost = :cost, offer_group_id = :offer_group_id, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_OFFER_SQL)
                        .param("id", id)
                        .param("cost", offer.getCost())
                        .param("offer_group_id", offer.getOfferGroupId())
                        .param("description", offer.getDescription())
                , "CATEGORY.ID.INVALID"
        );
    }

    // -------------------------------------- DELETE --------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_DELETE_SQL = """
                DELETE FROM offers WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_DELETE_SQL)
                        .param("id", id),
                "DELETE.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public Optional<Offer> get(Long id) {

        String SELECT_OFFER_HQL = """
                SELECT o FROM Offer o
                         WHERE o.id = :id
                """;

        return entityManager.createQuery(SELECT_OFFER_HQL, Offer.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Offer> getAll(OfferSearch search) {
        return buildQueryWithFilters(search).getResultList();
    }

    @Override
    public Page<Offer> getAllPaginated(Pageable pageable, OfferSearch search) {

        List<Offer> categories = buildQueryWithFilters(search)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(categories, pageable, categories.size());
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

    private TypedQuery<Offer> buildQueryWithFilters(OfferSearch search) {

        StringBuilder hql = new StringBuilder("SELECT o FROM Offer o");

        TypedQuery<Offer> query = entityManager.createQuery(hql.toString(), Offer.class);

        return query;
    }

}
