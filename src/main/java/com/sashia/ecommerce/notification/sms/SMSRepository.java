package com.sashia.ecommerce.notification.sms;

import com.sashia.ecommerce.notification.dto.SMSDTO;
import com.sashia.ecommerce.notification.dto.SMSType;
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
