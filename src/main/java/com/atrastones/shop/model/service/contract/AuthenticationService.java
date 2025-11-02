package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.AuthenticationDTO;

public interface AuthenticationService {

    /* ******************************** OPERATIONS ******************************** */

    AuthenticationDTO authenticateUser(AuthenticationDTO authentication, String panel);

    AuthenticationDTO attemptWithOtp(String phone, Integer otpCode);

    boolean checkTokenValidity(String token);

}
