package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.PaymentDTO;

public interface PaymentService {

    /* ******************************** CRUD ******************************** */

    Long create(PaymentDTO payment);

    /* ******************************** OPERATIONS ******************************** */
    //int amount, String authority, long userId
    PaymentDTO verifyPayment();

}
