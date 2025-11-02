package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.AttributeValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Jacksonized
public class AttributeDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    private String type;

    private Boolean isFilterable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ********************** Relation **********************

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CategoryDTO category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<AttributeValueDTO> attributeValues;

}
