package com.sashia.ecommerce.authentication;

import com.sashia.ecommerce.authentication.dto.AuthenticationDTO;
import com.sashia.ecommerce.authentication.dto.AuthenticationResponse;
import com.sashia.ecommerce.authentication.dto.LoginRequest;

public interface AuthenticationService {

    AuthenticationResponse authenticateAdmin(LoginRequest request);

    AuthenticationDTO attemptWithOTP(String phone, Integer otpCode);

    AuthenticationDTO authenticateCustomer(AuthenticationDTO authentication);

}
