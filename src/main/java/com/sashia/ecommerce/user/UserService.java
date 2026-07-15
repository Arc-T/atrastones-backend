package com.sashia.ecommerce.user;

import com.sashia.ecommerce.user.dto.UserDTO;

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


}