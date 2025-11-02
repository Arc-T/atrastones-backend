package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    // ****************************** CRUD ******************************

    Optional<UserDTO> get(Long id);

    Optional<UserDTO> getByPhone(String phone);

    void delete(Long id);

    long create(UserDTO user); // return the Jwt token

    long update(Long id, UserDTO user);

    // ****************************** RELATIONS ******************************

    Optional<UserDTO> getUserProfileInfo(Long id);

    // ****************************** PAGES ******************************

    // ****************************** OPERATIONS ******************************

    boolean existsByPhone(String phone);

}