package com.sashia.ecommerce.catalog.item.product;

import com.sashia.ecommerce.internal.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class ProductControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser(authorities = "test")
    void testGetAllProducts_returnsOk() throws Exception {
        mockMvc().perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

}
