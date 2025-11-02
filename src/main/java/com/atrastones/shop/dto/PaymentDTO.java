package com.atrastones.shop.dto;

import com.atrastones.shop.type.PaymentMethod;
import com.atrastones.shop.type.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class PaymentDTO {

    Long id;

    Long userId;

    Long orderId;

    PaymentMethod paymentMethod;

    Long amount;

    PaymentStatus status;

    String authority;

    String referenceId;

    String feeType;

    String fee;

    String description;

    LocalDateTime createdAt;

}
