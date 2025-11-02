package com.atrastones.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class SmsDTO {

    Long id;

    String phone;

    Long statusId;

    Long templateId;

    String text;

    String response;

    String description;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
