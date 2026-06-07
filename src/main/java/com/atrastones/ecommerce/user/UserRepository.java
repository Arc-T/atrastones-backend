package com.atrastones.ecommerce.user;

import com.atrastones.ecommerce.order.Order;
import com.atrastones.ecommerce.user.address.Address;
import com.atrastones.ecommerce.user.vip.VipGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository {

    // -------------------------------- CREATE --------------------------------

    Long create(UserDTO user);

    // -------------------------------- UPDATE --------------------------------

    void update(Long id, UserDTO user);

    // -------------------------------- SELECT --------------------------------

    Page<User> getAll(Pageable pageable);

    List<Order> getUserOrders(Long userId);

    List<VipGroup> getUserVipGroups(Long userId);

    List<Address> getUserAddresses(Long userId);

    // -------------------------------- OPERATIONS --------------------------------

    boolean exists(Long id);

    boolean existByPhone(String phone);

}
