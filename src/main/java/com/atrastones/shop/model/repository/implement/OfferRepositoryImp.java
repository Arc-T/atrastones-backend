package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.OfferDTO;
import com.atrastones.shop.model.repository.contract.OfferRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
                        .param("name", offer.get)
                        .param("cost", order.getAddressId())
                        .param("offer_group_id", order.getTotalPrice())
                        .param("description", order.getStatus())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(long id, OfferDTO offer) {

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

}
