package com.sashia.ecommerce.billing.payment.opg;

import com.sashia.ecommerce.billing.payment.Payment;
import com.sashia.ecommerce.billing.payment.dto.PaymentDTO;

import java.util.Optional;

interface PaymentRepository {

    // -------------------------------- CREATE --------------------------------------

    Long create(PaymentDTO payment);

    // -------------------------------- UPDATE --------------------------------------

    void update(Long id, PaymentDTO payment);

    // -------------------------------- SELECT --------------------------------------

    Optional<Payment> get(Long id);

    // -------------------------------- OPERATIONS ---------------------------------

    Long count();

    boolean exists(Long id);

}
