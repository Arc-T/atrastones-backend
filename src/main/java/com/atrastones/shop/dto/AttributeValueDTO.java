package com.atrastones.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class AttributeValueDTO {

    Long id;

    String value;

    LocalDateTime createdAt;

}
