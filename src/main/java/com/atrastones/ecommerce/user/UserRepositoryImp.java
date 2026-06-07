package com.atrastones.ecommerce.user;

import com.atrastones.ecommerce.order.Order;
import com.atrastones.ecommerce.user.address.Address;
import com.atrastones.ecommerce.user.vip.VipGroup;
import com.atrastones.infrastructure.db.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class UserRepositoryImp implements UserRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ---------------------------------------- CREATE ----------------------------------------

    @Override
    public Long create(UserDTO user) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO users (email, phone, user_group_id, gender, description)
                                  VALUES (:email, :phone, :user_group_id, :gender, :description)
                                """)
                        .param("email", user.email())
                        .param("phone", user.phone())
                        .param("user_group_id", user.userGroupId())
                        .param("gender", user.gender())
                        .param("description", user.description())
        );
    }

    // ---------------------------------------- UPDATE ----------------------------------------

    @Override
    public void update(Long id, UserDTO user) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE users
                                       SET first_name = :first_name, last_name = :last_name, email = :email, phone = :phone,
                                           user_group_id = :user_group_id, gender = :gender, description = :description
                                       WHERE id = :id
                                """)
                        .param("email", user.email())
                        .param("phone", user.phone())
                        .param("user_group_id", user.userGroupId())
                        .param("gender", user.gender())
                        .param("description", user.description())
                        .param("id", id)
        );
    }

    // ---------------------------------------- SELECT ----------------------------------------

    @Override
    public Page<User> getAll(Pageable pageable) {
        return PageableExecutionUtils.getPage(
                entityManager.createQuery("""
                                SELECT u FROM User u
                                         JOIN FETCH u.userGroup
                                """, User.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return entityManager.createQuery("""
                        SELECT o FROM Order o
                                 JOIN FETCH o.user u
                                 WHERE u.id = :userId
                        """, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<VipGroup> getUserVipGroups(Long userId) {
        return entityManager.createQuery("""
                        SELECT vg FROM VipGroup vg
                                  JOIN vg.users u
                                  WHERE u.id = :userId
                        """, VipGroup.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Address> getUserAddresses(Long userId) {
        return entityManager.createQuery("""
                        SELECT a FROM Address a
                                 JOIN FETCH a.user u
                                 WHERE u.id = :userId
                        """, Address.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // ---------------------------------------- OPERATION ----------------------------------------

    @Override
    public boolean exists(Long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM users WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    @Override
    public boolean existByPhone(String phone) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM users WHERE phone = :phone)")
                .param("phone", phone)
                .query(Boolean.class)
                .single();
    }

    public long count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM users")
                .query(Long.class)
                .single();
    }

}