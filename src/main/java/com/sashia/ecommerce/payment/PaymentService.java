package com.sashia.ecommerce.payment;

import com.sashia.ecommerce.payment.dto.PaymentDTO;

interface PaymentService {

    /* ******************************** CRUD ******************************** */

    Long create(PaymentDTO payment);

    /* ******************************** OPERATIONS ******************************** */
    //int amount, String authority, long userId
    PaymentDTO verifyPayment();

}
