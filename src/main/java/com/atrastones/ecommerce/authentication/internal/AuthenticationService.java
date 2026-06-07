package com.atrastones.ecommerce.authentication.internal;

import com.atrastones.ecommerce.authentication.common.AuthenticationDTO;
import com.atrastones.ecommerce.authentication.common.LoginDTO;

public interface AuthenticationService {

    /* =============================== OPERATIONS =============================== */

    AuthenticationDTO attemptWithOTP(String phone, Integer otpCode);

    AuthenticationDTO authenticateAdmin(LoginDTO credentials);

    AuthenticationDTO authenticateCustomer(AuthenticationDTO authentication);

}
