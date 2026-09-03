package com.sashia.ecommerce.platform.security;

import com.sashia.ecommerce.platform.security.user.SashiaUser;

import java.util.Optional;

public interface SashiaUserService {

    Optional<SashiaUser> findByPhone(String phone);

}
