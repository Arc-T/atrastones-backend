package com.atrastones.ecommerce.user;

import java.util.Optional;

public interface UserService {

    // ****************************** CRUD ******************************

    Optional<UserDTO> get(Long id);

    void delete(Long id);

    long create(UserDTO user); // return the Jwt token

    long update(Long id, UserDTO user);

    // ****************************** RELATIONS ******************************

    Optional<UserDTO> getUserProfileInfo(Long id);

    // ****************************** PAGES ******************************

    // ****************************** OPERATIONS ******************************

    boolean existsByPhone(String phone);

}