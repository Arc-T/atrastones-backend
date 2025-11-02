package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.SmsDTO;
import com.atrastones.shop.model.entity.Sms;
import com.atrastones.shop.model.repository.contract.SmsRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link SmsRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for sms. Uses {@link JdbcClient} for simple operations (create, update, delete, exists)
 * for performance, and {@link EntityManager} for select queries to leverage ORM benefits.
 */
@Repository
public class SmsRepositoryImp implements SmsRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public SmsRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // --------------------------------------- CREATE ---------------------------------------

    @Override
    public Long create(SmsDTO sms) {

        String INSERT_SMS_SQL = """
                INSERT INTO sms (phone, status_id, template_id, text, response, description)
                       VALUES (:phone, :status_id, :template_id, :text, :response, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_SMS_SQL)
                        .param("phone", sms.getPhone())
                        .param("status_id", sms.getStatusId())
                        .param("template_id", sms.getTemplateId())
                        .param("text", sms.getText())
                        .param("response", sms.getResponse())
                        .param("description", sms.getDescription())
        );
    }

    // --------------------------------------- UPDATE ---------------------------------------

    @Override
    public void update(Long id, SmsDTO sms) {

        String UPDATE_SMS_SQL = """
                UPDATE sms
                       SET phone = :phone, status_id = :status_id, template_id = :template_id,
                           text = :text, response = :response, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_SMS_SQL)
                        .param("phone", sms.getPhone())
                        .param("status_id", sms.getStatusId())
                        .param("template_id", sms.getTemplateId())
                        .param("text", sms.getText())
                        .param("response", sms.getResponse())
                        .param("description", sms.getDescription())
                , "SMS.ID.INVALID"
        );
    }

    // --------------------------------------- SELECT ---------------------------------------

    @Override
    public Page<Sms> getAllPaginated(Pageable pageable) {

        String SELECT_ALL_SMS_HQL = """
                SELECT s FROM Sms s
                         JOIN FETCH s.status
                         JOIN FETCH s.template
                """;

        return PageableExecutionUtils.getPage(
                entityManager.createQuery(SELECT_ALL_SMS_HQL, Sms.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    // --------------------------------------- OPERATION ---------------------------------------

    @Override
    public long count() {

        String COUNT_SMS_SQL = """
                SELECT COUNT(*) FROM sms
                """;

        return jdbcClient.sql(COUNT_SMS_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {

        String EXISTS_SMS_SQL = """
                SELECT EXISTS(SELECT 1 FROM sms WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_SMS_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();

    }

}
