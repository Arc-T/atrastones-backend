package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.AuthenticationDTO;

public interface AuthenticationService {

    /* ******************************** OPERATIONS ******************************** */

    AuthenticationDTO authenticateAdmin(AuthenticationDTO authentication);

    AuthenticationDTO authenticateCustomer(AuthenticationDTO authentication);

    AuthenticationDTO attemptWithOtp(String phone, Integer otpCode);

    boolean checkTokenValidity(String token);

}
