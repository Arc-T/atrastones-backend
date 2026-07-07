package com.sashia.ecommerce;

import com.sashia.ecommerce.internal.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class ProductControllerTest extends BaseControllerTest {

    @Test
    void testGetAllProducts_returnsOk() throws Exception {
        mockMvc().perform(MockMvcRequestBuilders.get("/products/brief")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

}
