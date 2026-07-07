package com.sashia.ecommerce.domain.notification.sms;

import com.sashia.ecommerce.domain.notification.common.SMSDTO;
import com.sashia.ecommerce.domain.notification.common.SMSType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SMSRepository {

    // ============================== CREATE ==============================

    Long create(SMSDTO sms);

    // ============================== UPDATE ==============================

    void update(Long id, SMSDTO sms);

    // ============================== SELECT ==============================

    Page<SMS> getAllPaginated(Pageable pageable);

    Optional<SMSTemplate> getTemplateByType(SMSType type);

    // ============================== OPERATION ===========================

    long count();

    boolean exists(Long id);

}
