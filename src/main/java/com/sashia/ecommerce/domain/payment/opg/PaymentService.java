package com.sashia.ecommerce.domain.payment.opg;

import com.sashia.ecommerce.domain.payment.common.PaymentDTO;

interface PaymentService {

    /* ******************************** CRUD ******************************** */

    Long create(PaymentDTO payment);

    /* ******************************** OPERATIONS ******************************** */
    //int amount, String authority, long userId
    PaymentDTO verifyPayment();

}
