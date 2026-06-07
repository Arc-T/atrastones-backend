package com.atrastones.ecommerce;

import com.atrastones.ecommerce.attribute.AttributeService;
import com.atrastones.ecommerce.attribute.common.AttributeCreateDTO;
import com.atrastones.ecommerce.attribute.common.AttributeDTO;
import com.atrastones.ecommerce.attribute.common.AttributeUpdateDTO;
import com.atrastones.ecommerce.internal.BaseControllerTest;
import com.atrastones.ecommerce.internal.Language;
import com.atrastones.ecommerce.internal.TestWithLocale;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Attribute Controller Tests")
class AttributeControllerTest extends BaseControllerTest {

    private static final String BASE_URL = "/attributes";
    private static final long EXISTING_ATTRIBUTE_ID = 1L;
    private static final long NON_EXISTENT_ATTRIBUTE_ID = 0L;
    private static final long INVALID_ATTRIBUTE_ID = 0L;
    private static final long INVALID_CATEGORY_ID = 0L;
    private static final long NEW_ATTRIBUTE_ID = 3L;

    @Autowired
    private AttributeService attributeService;

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetAllAttributes {

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return all attributes with status 200")
        void shouldReturnAllAttributes() throws Exception {
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(2))
                    .andExpect(jsonPath("$.page.totalElements").value(2));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return one filtered attributes when search param matches")
        void shouldReturnFilteredAttributes_whenSearchParamMatchesOne() throws Exception {
            // Given
            String searchTerm = "بند";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));

        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return all filtered attributes when search param matches")
        void shouldReturnFilteredAttributes_whenSearchParamMatchesWithMoreThanOne() throws Exception {
            // Given
            String searchTerm = "جنس";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(2));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return empty page when search param doesn't match")
        void shouldReturnEmptyPage_whenSearchParamDoesNotMatch() throws Exception {
            // Given
            String searchTerm = "non-existent-term";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.page.totalElements").value(0));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should support pagination parameters")
        void shouldSupportPagination() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "name,asc")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.page.number").value(0))
                    .andExpect(jsonPath("$.page.size").value(10));
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetAttributeById {

        @Test
        @WithMockUser(authorities = "READ_ATTRIBUTE")
        @DisplayName("Should return attribute when ID exists")
        void shouldReturnAttribute_whenIdExists() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(EXISTING_ATTRIBUTE_ID))
                    .andExpect(jsonPath("$.name").isString());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @TestWithLocale
        @WithMockUser(authorities = "READ_ATTRIBUTE")
        @DisplayName("Should return 422 when ID doesn't exist")
        void shouldReturnUnprocessableEntity_whenIdDoesNotExist(Language language) throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", NON_EXISTENT_ATTRIBUTE_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.message").value(message("attribute.not.found", language.getLocale())));
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateAttribute {

        @Test
        @WithMockUser(authorities = "CREATE_ATTRIBUTE")
        @DirtiesContext
        @DisplayName("Should create attribute and return 201 with location header")
        void shouldCreateAttribute() throws Exception {
            // Given
            AttributeCreateDTO request = new AttributeCreateDTO(
                    "size", 1L, "TEXT", false
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated())
                    .andExpect(header().string("Location", BASE_URL + "/" + NEW_ATTRIBUTE_ID));

            // Verify in database
            AttributeDTO createdAttribute = assertDoesNotThrow(() -> attributeService.read(NEW_ATTRIBUTE_ID));

            assertAll("Verify created attribute properties",
                    () -> assertThat(createdAttribute.name()).isEqualTo(request.name()),
                    () -> assertThat(createdAttribute.categoryId()).isEqualTo(request.categoryId()),
                    () -> assertThat(createdAttribute.type()).isEqualTo(request.type()),
                    () -> assertThat(createdAttribute.isFilterable()).isEqualTo(request.isFilterable())
            );
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_ATTRIBUTE")
        @DisplayName("Should return 400 when request validation fails")
        void shouldReturnBadRequest_whenValidationFails(Language language) throws Exception {
            // Given
            AttributeCreateDTO request = new AttributeCreateDTO(null, null, null, null);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("attribute.name.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.type").value(message("attribute.type.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.isFilterable").value(message("attribute.isFilterable.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.categoryId").value(message("category.id.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_ATTRIBUTE")
        @DisplayName("Should return 422 when category ID doesn't exist")
        void shouldReturnUnprocessableContent_whenCategoryNotFound(Language language) throws Exception {
            // Given
            AttributeCreateDTO request = new AttributeCreateDTO(
                    "test", INVALID_CATEGORY_ID, "TEXT", false
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message").value(message("category.not.found", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // Given
            AttributeCreateDTO request = new AttributeCreateDTO(
                    "size", 1L, "TEXT", false
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isForbidden());
        }

    }

    @Nested
    @DisplayName("PUT " + BASE_URL + "/{id}")
    class UpdateAttribute {

        @Test
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DirtiesContext
        @DisplayName("Should update attribute and return 204")
        void shouldUpdateAttribute() throws Exception {
            // Given
            AttributeUpdateDTO request = new AttributeUpdateDTO(
                    "size", 1L, "TEXT", true
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isNoContent());

            // Verify in database
            AttributeDTO updatedAttribute = assertDoesNotThrow(() -> attributeService.read(EXISTING_ATTRIBUTE_ID));

            assertAll("Verify updated attribute properties",
                    () -> assertThat(updatedAttribute.name()).isEqualTo(request.name()),
                    () -> assertThat(updatedAttribute.categoryId()).isEqualTo(request.categoryId()),
                    () -> assertThat(updatedAttribute.type()).isEqualTo(request.type()),
                    () -> assertThat(updatedAttribute.isFilterable()).isEqualTo(request.isFilterable())
            );
        }

        @Test
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DisplayName("Should return 422 when attribute ID doesn't exist")
        void shouldReturnUnprocessableContent_whenAttributeNotFound() throws Exception {
            // Given
            AttributeUpdateDTO request = new AttributeUpdateDTO(
                    "size", 1L, "TEXT", true
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", NON_EXISTENT_ATTRIBUTE_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DisplayName("Should return 400 when request validation fails")
        void shouldReturnBadRequest_whenValidationFails(Language language) throws Exception {
            // Given
            AttributeUpdateDTO request = new AttributeUpdateDTO(null, null, null, null);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DisplayName("Should return 422 when category ID doesn't exist")
        void shouldReturnUnprocessableContent_whenCategoryNotFound(Language language) throws Exception {
            // Given
            AttributeUpdateDTO request = new AttributeUpdateDTO(
                    "test", INVALID_CATEGORY_ID, "TEXT", false
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message").value(message("attribute.category.not.found", language.getLocale())));
        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteAttribute {

        @Test
        @Disabled("TODO: Implement delete endpoint and tests")
        @DisplayName("Should delete attribute and return 204")
        void shouldDeleteAttribute() {
            // TODO: Implement delete test
        }

    }

}