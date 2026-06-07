package com.atrastones.payment.opg;

import com.atrastones.payment.common.PaymentDTO;

interface PaymentService {

    /* ******************************** CRUD ******************************** */

    Long create(PaymentDTO payment);

    /* ******************************** OPERATIONS ******************************** */
    //int amount, String authority, long userId
    PaymentDTO verifyPayment();

}
