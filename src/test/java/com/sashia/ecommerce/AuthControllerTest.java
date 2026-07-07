package com.sashia.ecommerce;

import com.sashia.ecommerce.domain.authentication.common.LoginRequest;
import com.sashia.ecommerce.internal.BaseControllerTest;
import com.sashia.ecommerce.internal.Language;
import com.sashia.ecommerce.internal.TestWithLocale;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class AuthControllerTest extends BaseControllerTest {

    @Test
    void attemptLogin_ok() throws Exception {
        LoginRequest validCredentials = new LoginRequest("361629708", "1234321", false);

        mockMvc().perform(MockMvcRequestBuilders.post("/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper().writeValueAsString(validCredentials)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @TestWithLocale
    void attemptLogin_forbidden(Language language) throws Exception {
        LoginRequest invalidCredentials = new LoginRequest("00000000000", "88888888888", false);

        mockMvc().perform(MockMvcRequestBuilders.post("/authentication")
                        .locale(language.getLocale())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper().writeValueAsString(invalidCredentials)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message("authentication.invalid", language.getLocale())))
                .andDo(print());
    }

    @TestWithLocale
    void attemptLogin_badRequest(Language language) throws Exception {
        LoginRequest invalidCredentials = new LoginRequest(null, null, false);

        mockMvc().perform(MockMvcRequestBuilders.post("/authentication")
                        .locale(language.getLocale())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper().writeValueAsString(invalidCredentials)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message("invalid.method.params", language.getLocale())))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.details.fieldErrors.username")).value(message("username.required", language.getLocale())))
                .andExpect(MockMvcResultMatchers.jsonPath(("$.details.fieldErrors.password")).value(message("password.required", language.getLocale())))
                .andDo(print());
    }

    @TestWithLocale
    void attemptAuthorization_forbidden(Language language) throws Exception {
        Cookie expiredTokenCookie = new Cookie("token",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzNjE2Mjk3MDgiLCJpYXQiOjE3NjAzNDI1MjIsImV4cCI6MTc2MDM0NjEyMiwicm9sZXMiOlsiQUxMX1BFUk1JU1NJT05TIl19.ohE0Qt9eswYf-UCa3y2hs_YoSr5IkI78ho0exdGlFmI");

        mockMvc().perform(MockMvcRequestBuilders.post("/authentication/validate")
                        .locale(language.getLocale())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(expiredTokenCookie))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message("token.is.expired", language.getLocale())))
                .andDo(print());
    }

    @Test
    void attemptAuthorization_ok() throws Exception {
        Cookie nonExpiredToken = new Cookie("token",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzNjE2Mjk3MDgiLCJpYXQiOjE3Njk5NjYwMTQsImV4cCI6MzYxNzY5OTY2MDE0LCJwZXJtaXNzaW9ucyI6WyJGQUNUT1JfUEFTU1dPUkQiLCJST0xFX1NIT1BfT1dORVIiXX0.SkUUQEdnpQGD6ZbS9hwh_fhl_Zsv1dvoAQ2p3_Zy_yc");

        mockMvc().perform(MockMvcRequestBuilders.post("/authentication/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(nonExpiredToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

}