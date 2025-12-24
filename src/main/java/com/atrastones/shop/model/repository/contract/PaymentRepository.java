package com.atrastones.shop.model.repository.contract;

import java.util.Optional;

import com.atrastones.shop.dto.PaymentDTO;
import com.atrastones.shop.model.entity.Payment;

public interface PaymentRepository {

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
