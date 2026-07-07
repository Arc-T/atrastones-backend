package com.sashia.ecommerce.domain.notification.sms;

import com.sashia.ecommerce.common.util.JdbcUtils;
import com.sashia.ecommerce.domain.notification.common.SMSDTO;
import com.sashia.ecommerce.domain.notification.common.SMSType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SMSRepositoryImp implements SMSRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public SMSRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ================================= CREATE =================================

    @Override
    public Long create(SMSDTO sms) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO sms (phone, status_id, template_id, text, response, description)
                                       VALUES (:phone, :status_id, :template_id, :text, :response, :description)
                                """)
                        .param("phone", sms.phone())
                        .param("status_id", sms.statusId())
                        .param("template_id", sms.templateId())
                        .param("text", sms.text())
                        .param("response", sms.response())
                        .param("description", sms.description())
        );
    }

    // ================================= UPDATE =================================

    @Override
    public void update(Long id, SMSDTO sms) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE sms
                                SET phone = :phone, status_id = :status_id, template_id = :template_id,
                                    text = :text, response = :response, description = :description
                                WHERE id = :id
                                """)
                        .param("phone", sms.phone())
                        .param("status_id", sms.statusId())
                        .param("template_id", sms.templateId())
                        .param("text", sms.text())
                        .param("response", sms.response())
                        .param("description", sms.description())
                        .param("id", id)
        );
    }

    // ================================= SELECT =================================

    @Override
    public Page<SMS> getAllPaginated(Pageable pageable) {
        return PageableExecutionUtils.getPage(
                entityManager.createQuery("""
                                    SELECT s FROM SMS s
                                             JOIN FETCH s.status
                                             JOIN FETCH s.template
                                """, SMS.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public Optional<SMSTemplate> getTemplateByType(SMSType type) {
        return Optional.ofNullable(
                entityManager.createQuery("""
                                SELECT st FROM SMSTemplate st
                                          WHERE st.type = :type
                                """, SMSTemplate.class)
                        .setParameter("type", type)
                        .getSingleResultOrNull()
        );
    }

    // ================================= OPERATION =================================

    @Override
    public long count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM sms")
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM sms WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();

    }

}
