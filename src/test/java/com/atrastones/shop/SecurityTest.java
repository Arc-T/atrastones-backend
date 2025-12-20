package com.atrastones.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/dashboard"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testAdminLogin_returnsOk_whenAuthIsValid() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication?panel=admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "361629708",
                                  "password": "1234321"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void testAdminLogin_returnsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/authentication?panel=admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "361629708",
                                  "password": "1234234234321"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(print());
    }

    @Test
    void testAdminToken_returnsOk_whenTokenIsValid() throws Exception {

        Cookie tokenCookie = new Cookie("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzNjE2Mjk3MDgiLCJpYXQiOjE3NjAzNDI1MjIsImV4cCI6MTc2MDM0NjEyMiwicm9sZXMiOlsiQUxMX1BFUk1JU1NJT05TIl19.ohE0Qt9eswYf-UCa3y2hs_YoSr5IkI78ho0exdGlFmI");

        mockMvc.perform(MockMvcRequestBuilders.get("/authentication/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(tokenCookie))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN", authorities = "ALL_PERMISSIONS")
    void testValidation_returnsOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testValidation_returnsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}