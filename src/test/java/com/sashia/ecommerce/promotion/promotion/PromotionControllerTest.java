package com.sashia.ecommerce.promotion.promotion;

import com.sashia.ecommerce.internal.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Promotion Controller Tests")
public class PromotionControllerTest extends BaseControllerTest {

    private static final String BASE_URL = "/promotions";

    @Nested
    @DisplayName("GET " + BASE_URL)
    class GetAllPromotions {

        @Test
        @WithMockUser(authorities = "test")
        @DisplayName("Should return all promotions with status 200")
        void shouldReturnAllPromotions() throws Exception {
            mockMvc().perform(get(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

    }

}