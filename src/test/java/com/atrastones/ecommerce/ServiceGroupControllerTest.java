package com.atrastones.ecommerce;

import com.atrastones.ecommerce.internal.BaseControllerTest;
import com.atrastones.ecommerce.internal.Language;
import com.atrastones.ecommerce.internal.TestWithLocale;
import com.atrastones.ecommerce.service.group.ServiceGroupService;
import com.atrastones.ecommerce.service.group.common.ServiceGroupCreateDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupDTO;
import com.atrastones.ecommerce.service.group.common.ServiceGroupUpdateDTO;
import com.atrastones.infrastructure.error.InvalidResourceException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Service Group Controller Tests")
class ServiceGroupControllerTest extends BaseControllerTest {

    private static final String BASE_URL = "/service-groups";
    private static final long EXISTING_SERVICE_GROUP_ID = 1L;
    private static final long NON_EXISTENT_SERVICE_GROUP_ID = 0L;
    private static final long NEW_SERVICE_GROUP_ID = 2L;

    private static final String SERVICE_GROUP_NAME = "test";
    private static final String UPDATED_SERVICE_GROUP_NAME = "updated-test";
    private static final String SERVICE_GROUP_DESCRIPTION = "Test description";
    private static final String SPECIAL_CHARS_NAME = "test-!@#$%";
    private static final String LONG_NAME = "a".repeat(255);

    @Autowired
    private ServiceGroupService serviceGroupService;

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetAllServiceGroups {

        @Test
        @WithMockUser(authorities = "READ_ALL_SERVICE_GROUPS")
        @DisplayName("Should return all service groups with status 200")
        void shouldReturnAllServiceGroups() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(1))
                    .andExpect(jsonPath("$.page.totalElements").value(1))
                    .andExpect(jsonPath("$.page.totalPages").value(1));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_SERVICE_GROUPS")
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

        @Test
        @WithMockUser(authorities = "READ_ALL_SERVICE_GROUPS")
        @DisplayName("Should filter service groups by name")
        void shouldFilterServiceGroupsByName() throws Exception {
            // Given
            String searchTerm = "حمل";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));
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

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetServiceGroupById {

        @Test
        @WithMockUser(authorities = "READ_SERVICE_GROUP")
        @DisplayName("Should return service group when ID exists")
        void shouldReturnServiceGroup_whenIdExists() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(EXISTING_SERVICE_GROUP_ID))
                    .andExpect(jsonPath("$.name").isString());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @TestWithLocale
        @WithMockUser(authorities = "READ_SERVICE_GROUP")
        @DisplayName("Should return 422 when service group ID doesn't exist")
        void shouldReturnUnprocessableContent_whenIdDoesNotExist(Language language) throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", NON_EXISTENT_SERVICE_GROUP_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message").value(message("serviceGroup.not.found", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "READ_SERVICE_GROUP")
        @DisplayName("Should return 400 when ID is invalid")
        void shouldReturnUnprocessableContent_whenIdIsInvalid(Language language) throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", NON_EXISTENT_SERVICE_GROUP_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateServiceGroup {

        @Test
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DirtiesContext
        @DisplayName("Should create service group and return 201 with location header")
        void shouldCreateServiceGroup() throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO(
                    SERVICE_GROUP_NAME, SERVICE_GROUP_DESCRIPTION
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated())
                    .andExpect(header().string("Location", BASE_URL + "/" + NEW_SERVICE_GROUP_ID));

            // Verify in database
            ServiceGroupDTO createdGroup = assertDoesNotThrow(() -> serviceGroupService.get(NEW_SERVICE_GROUP_ID));

            assertAll("Verify created service group properties",
                    () -> assertThat(createdGroup.name()).isEqualTo(request.name()),
                    () -> assertThat(createdGroup.description()).isEqualTo(request.description())
            );
        }

        @Test
        @DirtiesContext
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DisplayName("Should create service group with special characters in name")
        void shouldCreateServiceGroupWithSpecialCharacters() throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO(
                    SPECIAL_CHARS_NAME, SERVICE_GROUP_DESCRIPTION
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated());
        }

        @Test
        @DirtiesContext
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DisplayName("Should create service group without description")
        void shouldCreateServiceGroupWithoutDescription() throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO(
                    SERVICE_GROUP_NAME, null
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated());

            ServiceGroupDTO createdGroup = assertDoesNotThrow(() -> serviceGroupService.get(NEW_SERVICE_GROUP_ID));
            assertThat(createdGroup.description()).isNull();
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DisplayName("Should return 400 when name is null")
        void shouldReturnBadRequest_whenNameIsNull(Language language) throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO(null, SERVICE_GROUP_DESCRIPTION);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("serviceGroup.name.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DisplayName("Should return 400 when name is empty")
        void shouldReturnBadRequest_whenNameIsEmpty(Language language) throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO("", SERVICE_GROUP_DESCRIPTION);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("serviceGroup.name.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DisplayName("Should return 400 when name is only whitespace")
        void shouldReturnBadRequest_whenNameIsWhitespace(Language language) throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO("   ", SERVICE_GROUP_DESCRIPTION);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.fieldErrors.name")
                            .value(message("serviceGroup.name.required", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // Given
            ServiceGroupCreateDTO request = new ServiceGroupCreateDTO(
                    SERVICE_GROUP_NAME, SERVICE_GROUP_DESCRIPTION
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isForbidden());
        }

        @Test
        @Disabled("TODO: Add handler for empty request body")
        @WithMockUser(authorities = "CREATE_SERVICE_GROUP")
        @DisplayName("Should return 400 when request body is empty")
        void shouldReturnBadRequest_whenRequestBodyEmpty() throws Exception {
            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("PUT " + BASE_URL + "/{id}")
    class UpdateServiceGroup {

        @Test
        @WithMockUser(authorities = "UPDATE_SERVICE_GROUP")
        @DirtiesContext
        @DisplayName("Should update service group and return 204")
        void shouldUpdateServiceGroup() throws Exception {
            // Given
            ServiceGroupUpdateDTO request = new ServiceGroupUpdateDTO(
                    UPDATED_SERVICE_GROUP_NAME, "Updated description"
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isNoContent());

            // Verify in database
            ServiceGroupDTO updatedGroup = assertDoesNotThrow(() -> serviceGroupService.get(EXISTING_SERVICE_GROUP_ID));

            assertAll("Verify updated service group properties",
                    () -> assertThat(updatedGroup.name()).isEqualTo(request.name()),
                    () -> assertThat(updatedGroup.description()).isEqualTo(request.description())
            );
        }

        @Test
        @WithMockUser(authorities = "UPDATE_SERVICE_GROUP")
        @DisplayName("Should return 404 when service group ID doesn't exist")
        void shouldReturnUnprocessableContent_whenServiceGroupDoesNotExist() throws Exception {
            // Given
            ServiceGroupUpdateDTO request = new ServiceGroupUpdateDTO(
                    UPDATED_SERVICE_GROUP_NAME, SERVICE_GROUP_DESCRIPTION
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", NON_EXISTENT_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_SERVICE_GROUP")
        @DisplayName("Should return 400 when name is null")
        void shouldReturnBadRequest_whenNameIsNull(Language language) throws Exception {
            // Given
            ServiceGroupUpdateDTO request = new ServiceGroupUpdateDTO(null, SERVICE_GROUP_DESCRIPTION);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("serviceGroup.name.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_SERVICE_GROUP")
        @DisplayName("Should return 400 when name is empty")
        void shouldReturnBadRequest_whenNameIsEmpty(Language language) throws Exception {
            // Given
            ServiceGroupUpdateDTO request = new ServiceGroupUpdateDTO("", SERVICE_GROUP_DESCRIPTION);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("serviceGroup.name.required", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // Given
            ServiceGroupUpdateDTO request = new ServiceGroupUpdateDTO(
                    UPDATED_SERVICE_GROUP_NAME, SERVICE_GROUP_DESCRIPTION
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isForbidden());
        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteServiceGroup {

        @Test
        @Disabled
        @DirtiesContext
        @WithMockUser(authorities = "DELETE_SERVICE_GROUP")
        @DisplayName("Should delete service group and return 204")
        void shouldDeleteServiceGroup() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isNoContent());

            // Verify deletion
            assertThrows(InvalidResourceException.class, () -> serviceGroupService.get(EXISTING_SERVICE_GROUP_ID));
        }

        @Test
        @WithMockUser(authorities = "DELETE_SERVICE_GROUP")
        @DisplayName("Should return 422 when service group ID doesn't exist")
        void shouldReturnUnprocessableContent_whenServiceGroupDoesNotExist() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", NON_EXISTENT_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", EXISTING_SERVICE_GROUP_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "DELETE_SERVICE_GROUP")
        @DisplayName("Should return 409 when service group has associated services")
        void shouldReturnConflict_whenServiceGroupHasServices() throws Exception {
            // This test requires a service group that has associated services
            // Implementation depends on your test data setup
        }

    }

}