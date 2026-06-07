package com.atrastones.ecommerce.authentication.common;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
        @NotBlank(message = "{username.required}")
        String username,
        @NotBlank(message = "{password.required}") @Length(min = 3, max = 20)
        String password
) {
}