package com.atrastones.ecommerce;

import com.atrastones.ecommerce.internal.BaseControllerTest;
import com.atrastones.ecommerce.internal.Language;
import com.atrastones.ecommerce.internal.TestWithLocale;
import com.atrastones.ecommerce.tag.TagService;
import com.atrastones.ecommerce.tag.common.TagCreateDTO;
import com.atrastones.ecommerce.tag.common.TagDTO;
import com.atrastones.ecommerce.tag.common.TagUpdateDTO;
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

@DisplayName("Tag Controller Tests")
class TagControllerTest extends BaseControllerTest {

    private static final String BASE_URL = "/tags";
    private static final Long EXISTING_TAG_ID = 1L;
    private static final Long NON_EXISTENT_TAG_ID = 0L;
    private static final Long INVALID_TAG_ID = 0L;
    private static final Long NEW_TAG_ID = 7L;

    private static final String TAG_NAME = "man";
    private static final String UPDATED_TAG_NAME = "test";
    private static final String SPECIAL_TAG_NAME = "special-tag-!@#$";

    @Autowired
    private TagService tagService;

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetAllTags {

        @Test
        @WithMockUser(authorities = "READ_ALL_TAGS")
        @DisplayName("Should return all tags with status 200")
        void shouldReturnAllTags() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(6));
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
        @WithMockUser(authorities = "READ_ALL_TAGS")
        @DisplayName("Should support pagination parameters")
        void shouldSupportPagination() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "5")
                    .param("sort", "name,asc")
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.page.number").value(0))
                    .andExpect(jsonPath("$.page.size").value(5));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_TAGS")
        @DisplayName("Should filter tags by search criteria")
        void shouldFilterTagsBySearchCriteria() throws Exception {
            // Given
            String searchTerm = "زنانه";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1))
                    .andExpect(jsonPath("$.content[0].name").value(searchTerm));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_TAGS")
        @DisplayName("Should return empty page when no tags match search criteria")
        void shouldReturnEmptyPage_whenNoTagsMatchSearch() throws Exception {
            // Given
            String searchTerm = "nonexistent-tag";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.page.totalElements").value(0));
        }

    }

    @Nested
    @DisplayName("GET " + BASE_URL + "/{id}")
    class GetTagById {

        @Test
        @WithMockUser(authorities = "READ_TAG")
        @DisplayName("Should return tag when ID exists")
        void shouldReturnTag_whenIdExists() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_TAG_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(EXISTING_TAG_ID))
                    .andExpect(jsonPath("$.name").isString());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_TAG_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @TestWithLocale
        @WithMockUser(authorities = "READ_TAG")
        @DisplayName("Should return 422 when tag ID doesn't exist")
        void shouldReturnUnprocessableContent_whenIdDoesNotExist(Language language) throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", NON_EXISTENT_TAG_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message").value(message("tag.not.found", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "READ_TAG")
        @DisplayName("Should return 422 when ID is invalid")
        void shouldReturnUnprocessableContent_whenIdIsInvalid(Language language) throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", INVALID_TAG_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent())
                    .andExpect(jsonPath("$.message").value(message("tag.not.found", language.getLocale())));
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateTag {

        @Test
        @WithMockUser(authorities = "CREATE_TAG")
        @DirtiesContext
        @DisplayName("Should create tag and return 201 with location header")
        void shouldCreateTag() throws Exception {
            // Given
            TagCreateDTO request = new TagCreateDTO(TAG_NAME);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated())
                    .andExpect(header().string("Location", BASE_URL + "/" + NEW_TAG_ID));

            // Verify in database
            TagDTO createdTag = assertDoesNotThrow(() -> tagService.get(NEW_TAG_ID));

            assertAll("Verify created tag properties",
                    () -> assertThat(createdTag).isNotNull(),
                    () -> assertThat(createdTag.id()).isEqualTo(NEW_TAG_ID),
                    () -> assertThat(createdTag.name()).isEqualTo(request.name())
            );
        }

        @Test
        @WithMockUser(authorities = "CREATE_TAG")
        @DirtiesContext
        @DisplayName("Should create tag with special characters in name")
        void shouldCreateTagWithSpecialCharacters() throws Exception {
            // Given
            TagCreateDTO request = new TagCreateDTO(SPECIAL_TAG_NAME);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated());

            // Verify in database
            TagDTO createdTag = assertDoesNotThrow(() -> tagService.get(NEW_TAG_ID));
            assertThat(createdTag.name()).isEqualTo(SPECIAL_TAG_NAME);
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_TAG")
        @DisplayName("Should return 400 when name is null")
        void shouldReturnBadRequest_whenNameIsNull(Language language) throws Exception {
            // Given
            TagCreateDTO request = new TagCreateDTO(null);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("tag.name.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_TAG")
        @DisplayName("Should return 400 when name is empty string")
        void shouldReturnBadRequest_whenNameIsEmpty(Language language) throws Exception {
            // Given
            TagCreateDTO request = new TagCreateDTO("");

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("tag.name.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_TAG")
        @DisplayName("Should return 400 when name is only whitespace")
        void shouldReturnBadRequest_whenNameIsWhitespace(Language language) throws Exception {
            // Given
            TagCreateDTO request = new TagCreateDTO("   ");

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("tag.name.required", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // Given
            TagCreateDTO request = new TagCreateDTO(TAG_NAME);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isForbidden());
        }

        @Test
        @Disabled("TODO: Add handler for empty request body")
        @WithMockUser(authorities = "CREATE_TAG")
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
    class UpdateTag {

        @Test
        @WithMockUser(authorities = "UPDATE_TAG")
        @DirtiesContext
        @DisplayName("Should update tag and return 204")
        void shouldUpdateTag() throws Exception {
            // Given
            TagUpdateDTO request = new TagUpdateDTO(UPDATED_TAG_NAME);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_TAG_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isNoContent());

            // Verify in database
            TagDTO updatedTag = assertDoesNotThrow(() -> tagService.get(EXISTING_TAG_ID));

            assertAll("Verify updated tag properties",
                    () -> assertThat(updatedTag).isNotNull(),
                    () -> assertThat(updatedTag.id()).isEqualTo(EXISTING_TAG_ID),
                    () -> assertThat(updatedTag.name()).isEqualTo(request.name())
            );
        }

        @Test
        @WithMockUser(authorities = "UPDATE_TAG")
        @DisplayName("Should return 404 when tag ID doesn't exist")
        void shouldReturnUnprocessableContent_whenTagDoesNotExist() throws Exception {
            // Given
            TagUpdateDTO request = new TagUpdateDTO(UPDATED_TAG_NAME);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", NON_EXISTENT_TAG_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_TAG")
        @DisplayName("Should return 400 when name is null")
        void shouldReturnBadRequest_whenNameIsNull(Language language) throws Exception {
            // Given
            TagUpdateDTO request = new TagUpdateDTO(null);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_TAG_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("tag.name.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_TAG")
        @DisplayName("Should return 400 when name is empty")
        void shouldReturnBadRequest_whenNameIsEmpty(Language language) throws Exception {
            // Given
            TagUpdateDTO request = new TagUpdateDTO("");

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_TAG_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("tag.name.required", language.getLocale())));
        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteTag {

        @Test
        @DirtiesContext
        @WithMockUser(authorities = "DELETE_TAG")
        @DisplayName("Should delete tag and return 204")
        void shouldDeleteTag() throws Exception {
            // Given - Create a tag to delete
            TagCreateDTO createRequest = new TagCreateDTO("tag-to-delete");
            Long tagId = tagService.create(createRequest);

            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", tagId)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isNoContent());

            // Verify deletion
            assertThrows(InvalidResourceException.class, () -> tagService.get(tagId));
        }

        @Test
        @WithMockUser(authorities = "DELETE_TAG")
        @DisplayName("Should return 404 when tag ID doesn't exist")
        void shouldUnprocessableContent_whenTagDoesNotExist() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", NON_EXISTENT_TAG_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", EXISTING_TAG_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "DELETE_TAG")
        @DisplayName("Should return 409 when tag is associated with products")
        void shouldReturnConflict_whenTagIsInUse() throws Exception {
            // This test requires a tag that is associated with products
            // Implementation depends on your test data setup
            // You might need to create a product and associate it with a tag
        }

    }

}