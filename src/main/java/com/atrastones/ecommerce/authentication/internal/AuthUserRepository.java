package com.atrastones.ecommerce.authentication.internal;

import java.util.Optional;

public interface AuthUserRepository {

    Optional<AuthUser> findByPhone(String phone);

}