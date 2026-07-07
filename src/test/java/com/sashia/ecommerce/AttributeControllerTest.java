package com.sashia.ecommerce;

import com.sashia.ecommerce.domain.attribute.AttributeService;
import com.sashia.ecommerce.domain.attribute.AttributeType;
import com.sashia.ecommerce.domain.attribute.dto.AttributeCreateRequest;
import com.sashia.ecommerce.domain.attribute.dto.AttributeResponse;
import com.sashia.ecommerce.domain.attribute.dto.AttributeUpdateRequest;
import com.sashia.ecommerce.domain.attribute.value.AttributeValueRequest;
import com.sashia.ecommerce.internal.BaseControllerTest;
import com.sashia.ecommerce.internal.Language;
import com.sashia.ecommerce.internal.TestWithLocale;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Attribute Controller Tests")
class AttributeControllerTest extends BaseControllerTest {

    private static final String BASE_URL = "/attributes";
    private static final long EXISTING_ATTRIBUTE_ID = 1L;
    private static final long NON_EXISTENT_ATTRIBUTE_ID = 0L;
    private static final long INVALID_CATEGORY_ID = 0L;
    private static final long VALID_CATEGORY_ID = 1L;
    private static final long NEW_ATTRIBUTE_ID = 10L;

    @Autowired
    private AttributeService attributeService;

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetAllAttributes {

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return all attributes with status 200")
        void shouldReturnAllAttributes() throws Exception {
            mockMvc().perform(get(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(9))
                    .andExpect(jsonPath("$.page.totalElements").value(9));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return one filtered attributes when search param matches")
        void shouldReturnFilteredAttributes_whenSearchParamMatchesOne() throws Exception {
            String searchTerm = "بند";

            mockMvc().perform(get(BASE_URL)
                            .param("name", searchTerm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));

        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return all filtered attributes when search param matches")
        void shouldReturnFilteredAttributes_whenSearchParamMatchesWithMoreThanOne() throws Exception {
            String searchTerm = "جنس";

            mockMvc().perform(get(BASE_URL)
                            .param("name", searchTerm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(2));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should return empty page when search param doesn't match")
        void shouldReturnEmptyPage_whenSearchParamDoesNotMatch() throws Exception {
            String searchTerm = "non-existent-term";

            mockMvc().perform(get(BASE_URL)
                            .param("name", searchTerm)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.page.totalElements").value(0));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            mockMvc().perform(get(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTES")
        @DisplayName("Should support pagination parameters")
        void shouldSupportPagination() throws Exception {
            mockMvc().perform(get(BASE_URL)
                            .param("page", "0")
                            .param("size", "10")
                            .param("sort", "name,asc")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.page.number").value(0))
                    .andExpect(jsonPath("$.page.size").value(10));
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetAttributeById {

        @Test
        @WithMockUser(authorities = "READ_ATTRIBUTE")
        @DisplayName("Should return one filtered attributes when search param matches")
        void shouldReturnExistingAttribute() throws Exception {
            mockMvc().perform(get(BASE_URL + "/" + EXISTING_ATTRIBUTE_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
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
            AttributeCreateRequest request = validCreateRequest();

            mockMvc().perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string(HttpHeaders.LOCATION, BASE_URL + "/" + NEW_ATTRIBUTE_ID));

            AttributeResponse created = attributeService.read(NEW_ATTRIBUTE_ID)
                    .orElseThrow();

            assertAll(
                    () -> assertThat(created.name()).isEqualTo(request.name()),
                    () -> assertThat(created.categoryId()).isEqualTo(request.categoryId()),
                    () -> assertThat(created.type()).isEqualTo(request.type()),
                    () -> assertThat(created.isFilterable()).isEqualTo(request.isFilterable()),
                    () -> assertThat(created.values().size()).isEqualTo(request.values().size())
            );
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_ATTRIBUTE")
        @DisplayName("Should return 400 when request validation fails")
        void shouldReturnBadRequest_whenValidationFails(Language language) throws Exception {
            mockMvc().perform(post(BASE_URL)
                            .locale(language.getLocale())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(invalidCreateRequest())))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message")
                            .value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name")
                            .value(message("attribute.name.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.type")
                            .value(message("attribute.type.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.isFilterable")
                            .value(message("attribute.isFilterable.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.categoryId")
                            .value(message("category.id.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_ATTRIBUTE")
        @DisplayName("Should return 422 when category does not exist")
        void shouldReturnUnprocessableEntity_whenCategoryDoesNotExist(Language language) throws Exception {
            mockMvc().perform(post(BASE_URL)
                            .locale(language.getLocale())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(createRequest(INVALID_CATEGORY_ID))))
                    .andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message")
                            .value(message("category.not.found", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            mockMvc().perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(validCreateRequest())))
                    .andExpect(status().isForbidden());
        }

        private AttributeCreateRequest validCreateRequest() {
            return createRequest(VALID_CATEGORY_ID);
        }

        private AttributeCreateRequest createRequest(Long categoryId) {
            return new AttributeCreateRequest(
                    "size",
                    categoryId,
                    AttributeType.SELECT,
                    true,
                    null,
                    List.of(
                            new AttributeValueRequest("2X"),
                            new AttributeValueRequest("3X"),
                            new AttributeValueRequest("LG"),
                            new AttributeValueRequest("MD"),
                            new AttributeValueRequest("SM")
                    )
            );
        }

        private AttributeCreateRequest invalidCreateRequest() {
            return new AttributeCreateRequest(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
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
            AttributeUpdateRequest request = createRequest(EXISTING_ATTRIBUTE_ID);

            mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(request)))
                    .andExpect(status().isNoContent());

            // Verify in database
            AttributeResponse updatedAttribute = assertDoesNotThrow(() -> attributeService.read(EXISTING_ATTRIBUTE_ID)
                    .orElseThrow());

            assertAll("Verify updated attribute properties",
                    () -> assertThat(updatedAttribute.name()).isEqualTo(request.name()),
                    () -> assertThat(updatedAttribute.categoryId()).isEqualTo(request.categoryId()),
                    () -> assertThat(updatedAttribute.type()).isEqualTo(request.type()),
                    () -> assertThat(updatedAttribute.isFilterable()).isEqualTo(request.isFilterable()),
                    () -> assertThat(updatedAttribute.values().size()).isEqualTo(request.values().size())
            );
        }

        @Test
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DisplayName("Should return 422 when attribute ID doesn't exist")
        void shouldReturnUnprocessableContent_whenAttributeNotFound() throws Exception {
            AttributeUpdateRequest request = new AttributeUpdateRequest(
                    "size", 1L, AttributeType.TEXT, true, null, null
            );

            mockMvc().perform(put(BASE_URL + "/{id}", NON_EXISTENT_ATTRIBUTE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(request)))
                    .andExpect(status().isUnprocessableContent());
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DisplayName("Should return 400 when request validation fails")
        void shouldReturnBadRequest_whenValidationFails(Language language) throws Exception {
            AttributeUpdateRequest request = new AttributeUpdateRequest(null, null, null, null,
                    null, null);

            mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                            .locale(language.getLocale())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_ATTRIBUTE")
        @DisplayName("Should return 422 when category ID doesn't exist")
        void shouldReturnUnprocessableContent_whenCategoryNotFound(Language language) throws Exception {
            AttributeUpdateRequest request = new AttributeUpdateRequest(
                    "test", INVALID_CATEGORY_ID, AttributeType.TEXT, false, null, null
            );

            mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_ATTRIBUTE_ID)
                            .locale(language.getLocale())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(request)))
                    .andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message").value(message("category.not.found", language.getLocale())));
        }

        private AttributeUpdateRequest createRequest(Long categoryId) {
            return new AttributeUpdateRequest(
                    "size",
                    categoryId,
                    AttributeType.SELECT,
                    true,
                    null,
                    List.of(
                            new AttributeValueRequest("2X"),
                            new AttributeValueRequest("3X"),
                            new AttributeValueRequest("LG"),
                            new AttributeValueRequest("MD"),
                            new AttributeValueRequest("SM")
                    )
            );
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

    @Nested
    @DisplayName("GET " + BASE_URL + "/types")
    class GetAllAttributeTypes {

        @Test
        @WithMockUser(authorities = "READ_ALL_ATTRIBUTE_TYPES")
        @DisplayName("Should return all filtered attributes when search param matches")
        void shouldReturnAlAttributeTypes() throws Exception {
            mockMvc().perform(get(BASE_URL + "/types")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

    }

}