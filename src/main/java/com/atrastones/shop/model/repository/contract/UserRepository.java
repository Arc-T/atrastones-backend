package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.*;
import com.atrastones.shop.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    // -------------------------------- CREATE --------------------------------

    Long create(UserDTO user);

    // -------------------------------- UPDATE --------------------------------

    void update(Long id, UserDTO user);

    // -------------------------------- SELECT --------------------------------

    User getByPhone(String phone);

    Page<User> getAll(Pageable pageable);

    Optional<UserGroup> getUserGroup(Long userId);

    List<Order> getUserOrders(Long userId);

    List<VipGroup> getUserVipGroups(Long userId);

    List<Address> getUserAddresses(Long userId);

    // -------------------------------- OPERATIONS --------------------------------

    boolean exists(Long id);

    boolean existByPhone(String phone);

}
