package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class TagDTO {

    Long id;

    String name;

    // *********************** DTO ***********************

    public static TagDTO toDTO(Tag tag){
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

}
