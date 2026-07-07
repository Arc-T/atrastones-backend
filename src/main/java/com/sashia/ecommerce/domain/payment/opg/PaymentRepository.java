package com.sashia.ecommerce.domain.payment.opg;

import com.sashia.ecommerce.domain.payment.common.PaymentDTO;

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
