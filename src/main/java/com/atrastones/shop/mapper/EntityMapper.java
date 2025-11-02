package com.atrastones.shop.mapper;

/**
 * Contract for bidirectional transformation between domain entities and data transfer objects.
 *
 * @param <ENTITY> The domain entity type representing business data and logic
 * @param <DTO> The data transfer object type for API contracts and external communication
 */
public interface EntityMapper<ENTITY, DTO> {

    /**
     * Transforms a domain entity into its corresponding data transfer object.
     * Used when returning data from the business layer to external consumers.
     *
     * @param entity the domain entity to transform, must not be null
     * @return the data transfer object representation of the entity
     * @throws IllegalArgumentException if entity is null
     */
    DTO toDTO(ENTITY entity);

    /**
     * Transforms a domain entity into a data transfer object with all fields populated.
     * Used when complete entity representation is required, including all relationships,
     * computed properties, and metadata. Suitable for detailed views and full data exports.
     *
     * @param entity the domain entity to transform, must not be null
     * @return the DTO with all fields populated
     * @throws IllegalArgumentException if entity is null
     */
    DTO toFullDTO(ENTITY entity);

    /**
     * Transforms a data transfer object into its corresponding domain entity.
     * Used when receiving external data to be processed by the business layer.
     *
     * @param dto the data transfer object to transform, must not be null
     * @return the domain entity representation of the DTO
     * @throws IllegalArgumentException if dto is null
     */
    ENTITY toEntity(DTO dto);

}