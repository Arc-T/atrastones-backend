package com.sashia.ecommerce.domain.authentication.core;

import com.sashia.ecommerce.domain.authentication.common.AuthenticationDTO;
import com.sashia.ecommerce.domain.authentication.common.AuthenticationResponse;
import com.sashia.ecommerce.domain.authentication.common.LoginRequest;

public interface AuthenticationService {

    /* =============================== OPERATIONS =============================== */

    AuthenticationDTO attemptWithOTP(String phone, Integer otpCode);

    AuthenticationResponse authenticateAdmin(LoginRequest request);

    AuthenticationDTO authenticateCustomer(AuthenticationDTO authentication);

}
