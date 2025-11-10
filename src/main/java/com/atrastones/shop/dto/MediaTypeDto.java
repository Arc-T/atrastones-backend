package com.atrastones.shop.dto;


import com.atrastones.shop.model.entity.MediaType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class MediaTypeDto {

    Long id;

    String name;

    String description;

    public static MediaTypeDto toDTO(MediaType mediaType) {
        return MediaTypeDto.builder()
                .id(mediaType.getId())
                .name(mediaType.getName())
                .description(mediaType.getDescription())
                .build();
    }

}
