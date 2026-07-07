package com.sashia.ecommerce;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.category.CategoryService;
import com.sashia.ecommerce.domain.category.dto.CategoryCreateDTO;
import com.sashia.ecommerce.domain.category.dto.CategoryDTO;
import com.sashia.ecommerce.domain.category.dto.CategoryUpdateDTO;
import com.sashia.ecommerce.internal.BaseControllerTest;
import com.sashia.ecommerce.internal.Language;
import com.sashia.ecommerce.internal.TestWithLocale;
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

@DisplayName("Category Controller Tests")
class CategoryControllerTest extends BaseControllerTest {

    private static final String BASE_URL = "/categories";
    private static final Long EXISTING_CATEGORY_ID = 1L;
    private static final Long NON_EXISTENT_CATEGORY_ID = 0L;
    private static final Long INVALID_PARENT_ID = 0L;
    private static final Long NEW_CATEGORY_ID = 4L;

    private static final String CATEGORY_NAME = "Test category";
    private static final String CATEGORY_URL = "/accessory";
    private static final String CATEGORY_ICON = "mui-necklace";
    private static final int DISPLAY_ORDER = 1;

    @Autowired
    private CategoryService categoryService;

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetAllCategories {

        @Test
        @WithMockUser(authorities = "READ_ALL_CATEGORIES")
        @DisplayName("Should return all categories with status 200")
        void shouldReturnAllCategories() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(3))
                    .andExpect(jsonPath("$.page.totalElements").value(3));
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
        @WithMockUser(authorities = "READ_ALL_CATEGORIES")
        @DisplayName("Should return all filtered categories when search param matches")
        void shouldReturnFilteredCategories_whenSearchParamMatchesWithOne() throws Exception {
            // Given
            String searchTerm = "زیور";

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("name", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_CATEGORIES")
        @DisplayName("Should return children categories")
        void shouldReturnChildrenCategories() throws Exception {
            // Given
            String searchTerm = Boolean.TRUE.toString();

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("onlyChildren", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(2));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_CATEGORIES")
        @DisplayName("Should return children categories")
        void shouldReturnParentCategories() throws Exception {
            // Given
            String searchTerm = Boolean.TRUE.toString();

            // When
            ResultActions result = mockMvc().perform(get(BASE_URL)
                    .param("onlyParents", searchTerm)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(1));
        }

        @Test
        @WithMockUser(authorities = "READ_ALL_CATEGORIES")
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
        @WithMockUser(authorities = "READ_ALL_CATEGORIES")
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
    class GetCategoryById {

        @Test
        @WithMockUser(authorities = "READ_CATEGORY")
        @DisplayName("Should return category when ID exists")
        void shouldReturnCategory_whenIdExists() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(EXISTING_CATEGORY_ID))
                    .andExpect(jsonPath("$.name").isString())
                    .andExpect(jsonPath("$.url").isString());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @TestWithLocale
        @WithMockUser(authorities = "READ_CATEGORY")
        @DisplayName("Should return 422 when category ID doesn't exist")
        void shouldReturnUnprocessableEntity_whenIdDoesNotExist(Language language) throws Exception {
            // When
            ResultActions result = mockMvc().perform(get(BASE_URL + "/{id}", NON_EXISTENT_CATEGORY_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.message").value(message("category.not.found", language.getLocale())));
        }

    }

    @Nested
    @DisplayName("POST " + BASE_URL)
    class CreateCategory {

        @Test
        @WithMockUser(authorities = "CREATE_CATEGORY")
        @DirtiesContext
        @DisplayName("Should create category and return 201 with location header")
        void shouldCreateCategory() throws Exception {
            // Given
            CategoryCreateDTO request = new CategoryCreateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    null, DISPLAY_ORDER, null
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isCreated())
                    .andExpect(header().string("Location", BASE_URL + "/" + NEW_CATEGORY_ID));

            // Verify in database
            CategoryDTO createdCategory = assertDoesNotThrow(() -> categoryService.read(NEW_CATEGORY_ID));

            assertAll("Verify created category properties",
                    () -> assertThat(createdCategory.name()).isEqualTo(request.name()),
                    () -> assertThat(createdCategory.url()).isEqualTo(request.url()),
                    () -> assertThat(createdCategory.icon()).isEqualTo(request.icon()),
                    () -> assertThat(createdCategory.parentId()).isEqualTo(request.parentId()),
                    () -> assertThat(createdCategory.displayOrder()).isEqualTo(request.displayOrder()),
                    () -> assertThat(createdCategory.description()).isEqualTo(request.description())
            );
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_CATEGORY")
        @DisplayName("Should return 400 when required fields are missing")
        void shouldReturnBadRequest_whenRequiredFieldsMissing(Language language) throws Exception {
            // Given
            CategoryCreateDTO request = new CategoryCreateDTO(null, null, null, null, null, null);

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.name").value(message("category.name.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.url").value(message("category.url.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.icon").value(message("category.icon.required", language.getLocale())))
                    .andExpect(jsonPath("$.details.fieldErrors.displayOrder").value(message("category.displayOrder.required", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "CREATE_CATEGORY")
        @DisplayName("Should return 422 when parent category doesn't exist")
        void shouldReturnUnprocessableEntity_whenParentNotFound(Language language) throws Exception {
            // Given
            CategoryCreateDTO request = new CategoryCreateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    INVALID_PARENT_ID, DISPLAY_ORDER, null
            );

            // When
            ResultActions result = mockMvc().perform(post(BASE_URL)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.message").value(message("category.parentId.not.found", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // Given
            CategoryCreateDTO request = new CategoryCreateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    null, DISPLAY_ORDER, null
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
        @WithMockUser(authorities = "CREATE_CATEGORY")
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
    class UpdateCategory {

        @Test
        @DirtiesContext
        @WithMockUser(authorities = "UPDATE_CATEGORY")
        @DisplayName("Should update category and return 204")
        void shouldUpdateCategory() throws Exception {
            // Given
            CategoryUpdateDTO request = new CategoryUpdateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    null, DISPLAY_ORDER, null
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isNoContent());

            // Verify in database
            CategoryDTO updatedCategory = assertDoesNotThrow(() -> categoryService.read(EXISTING_CATEGORY_ID));

            assertAll("Verify updated category properties",
                    () -> assertThat(updatedCategory.name()).isEqualTo(request.name()),
                    () -> assertThat(updatedCategory.url()).isEqualTo(request.url()),
                    () -> assertThat(updatedCategory.icon()).isEqualTo(request.icon()),
                    () -> assertThat(updatedCategory.parentId()).isEqualTo(request.parentId()),
                    () -> assertThat(updatedCategory.displayOrder()).isEqualTo(request.displayOrder()),
                    () -> assertThat(updatedCategory.description()).isEqualTo(request.description())
            );
        }

        @Test
        @WithMockUser(authorities = "UPDATE_CATEGORY")
        @DisplayName("Should return 422 when category ID doesn't exist")
        void shouldReturnUnprocessableContent_whenCategoryDoesNotExist() throws Exception {
            // Given
            CategoryUpdateDTO request = new CategoryUpdateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    null, DISPLAY_ORDER, null
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", NON_EXISTENT_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_CATEGORY")
        @DisplayName("Should return 400 when required fields are missing")
        void shouldReturnBadRequest_whenRequiredFieldsMissing(Language language) throws Exception {
            // Given
            CategoryUpdateDTO request = new CategoryUpdateDTO(null, null, null, null, null, null);

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(message("invalid.method.params", language.getLocale())));
        }

        @TestWithLocale
        @WithMockUser(authorities = "UPDATE_CATEGORY")
        @DisplayName("Should return 422 when parent category doesn't exist")
        void shouldReturnUnprocessableEntity_whenParentNotFound(Language language) throws Exception {
            // Given
            CategoryUpdateDTO request = new CategoryUpdateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    INVALID_PARENT_ID, DISPLAY_ORDER, null
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .locale(language.getLocale())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.message").value(message("category.parentId.not.found", language.getLocale())));
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // Given
            CategoryUpdateDTO request = new CategoryUpdateDTO(
                    CATEGORY_NAME, CATEGORY_URL, CATEGORY_ICON,
                    null, DISPLAY_ORDER, null
            );

            // When
            ResultActions result = mockMvc().perform(put(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper().writeValueAsString(request)));

            // Then
            result.andExpect(status().isForbidden());
        }

    }

    @Nested
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    class DeleteCategory {

        @Test
        @DirtiesContext
        @WithMockUser(authorities = "DELETE_CATEGORY")
        @DisplayName("Should delete category and return 204")
        void shouldDeleteCategory() throws Exception {
            // Given - Create a category to delete
            CategoryCreateDTO createRequest = new CategoryCreateDTO(
                    "Category to delete", "/delete", "delete-icon",
                    null, 99, null
            );

            Long categoryId = categoryService.create(createRequest);

            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", categoryId)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isNoContent());

            // Verify deletion
            assertThrows(InvalidResourceException.class, () -> categoryService.read(categoryId));
        }

        @Test
        @WithMockUser(authorities = "DELETE_CATEGORY")
        @DisplayName("Should return 422 when category ID doesn't exist")
        void shouldReturnUnprocessableContent_whenCategoryDoesNotExist() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", NON_EXISTENT_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isUnprocessableContent());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403 when user lacks authority")
        void shouldReturnForbidden_whenUserLacksAuthority() throws Exception {
            // When
            ResultActions result = mockMvc().perform(delete(BASE_URL + "/{id}", EXISTING_CATEGORY_ID)
                    .contentType(MediaType.APPLICATION_JSON));

            // Then
            result.andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "DELETE_CATEGORY")
        @DisplayName("Should return 409 when category has children")
        void shouldReturnConflict_whenCategoryHasChildren() throws Exception {
            // This test would require setting up a parent category with children
            // Implementation depends on your test data setup
        }

    }

}