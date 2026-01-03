package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.UserDTO;
import com.atrastones.shop.model.entity.*;
import com.atrastones.shop.model.repository.contract.UserRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ---------------------------------------- CREATE ----------------------------------------

    @Override
    public Long create(UserDTO user) {

        String INSERT_USER_SQL = """
                INSERT INTO users (first_name, last_name, email, phone, password, user_group_id, gender, description)
                       VALUES (:first_name, :last_name, :email, :phone, :password, :user_group_id, :gender,
                               :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_USER_SQL)
                        .param("first_name", user.firstName())
                        .param("last_name", user.lastName())
                        .param("email", user.email())
                        .param("phone", user.phone())
                        .param("password", user.password())
                        .param("user_group_id", user.userGroupId())
                        .param("gender", user.gender())
                        .param("description", user.description())
        );
    }

    // ---------------------------------------- UPDATE ----------------------------------------

    @Override
    public void update(Long id, UserDTO user) {

        String UPDATE_USER_SQL = """
                UPDATE users
                       SET first_name = :first_name, last_name = :last_name, email = :email, phone = :phone,
                           password = :password, user_group_id = :user_group_id, gender = :gender,
                           description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_USER_SQL)
                        .param("first_name", user.firstName())
                        .param("last_name", user.lastName())
                        .param("email", user.email())
                        .param("phone", user.phone())
                        .param("password", user.password())
                        .param("user_group_id", user.userGroupId())
                        .param("gender", user.gender())
                        .param("description", user.description())
                        .param("id", id)
                , "USER.ID.INVALID"
        );
    }

    // ---------------------------------------- SELECT ----------------------------------------

    @Override
    public Page<User> getAll(Pageable pageable) {

        String SELECT_ALL_USERS_HQL = """
                SELECT u FROM User u
                         JOIN FETCH u.userGroup
                """;

        return PageableExecutionUtils.getPage(
                entityManager.createQuery(SELECT_ALL_USERS_HQL, User.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public User getByPhone(String phone) {

        String SELECT_USER_BY_PHONE_HQL = """
                SELECT u FROM User u
                         JOIN FETCH u.userGroup ug
                         JOIN FETCH ug.roles r
                         JOIN FETCH r.permissions
                         JOIN FETCH u.shops
                         WHERE u.phone = :phone
                """;

        return entityManager.createQuery(SELECT_USER_BY_PHONE_HQL, User.class)
                .setParameter("phone", phone)
                .getSingleResult();
    }

    @Override
    public Optional<UserGroup> getUserGroup(Long userId) {

        String SELECT_USER_GROUP_HQL = """
                SELECT ug FROM UserGroup ug
                          JOIN FETCH ug.users u
                          WHERE u.id = :userId
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_USER_GROUP_HQL, UserGroup.class)
                        .setParameter("userId", userId)
                        .getSingleResult()
        );
    }

    @Override
    public List<Order> getUserOrders(Long userId) {

        String SELECT_USER_ORDERS_HQL = """
                SELECT o FROM Order o
                         JOIN FETCH o.user u
                         WHERE u.id = :userId
                """;

        return entityManager.createQuery(SELECT_USER_ORDERS_HQL, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<VipGroup> getUserVipGroups(Long userId) {

        String SELECT_USER_VIP_GROUPS_HQL = """
                SELECT vg FROM VipGroup vg
                          JOIN vg.users u
                          WHERE u.id = :userId
                """;

        return entityManager.createQuery(SELECT_USER_VIP_GROUPS_HQL, VipGroup.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Address> getUserAddresses(Long userId) {

        String SELECT_USER_ADDRESSES_HQL = """
                SELECT a FROM Address a
                         JOIN FETCH a.user u
                         WHERE u.id = :userId
                """;

        return entityManager.createQuery(SELECT_USER_ADDRESSES_HQL, Address.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // ---------------------------------------- OPERATION ----------------------------------------

    @Override
    public boolean exists(Long id) {

        String EXISTS_USER_BY_ID_SQL = """
                SELECT EXISTS(SELECT 1 FROM users WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_USER_BY_ID_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    @Override
    public boolean existByPhone(String phone) {

        String EXISTS_USER_BY_PHONE_SQL = """
                SELECT EXISTS(SELECT 1 FROM users WHERE phone = :phone)
                """;

        return jdbcClient.sql(EXISTS_USER_BY_PHONE_SQL)
                .param("phone", phone)
                .query(Boolean.class)
                .single();
    }

    public long count() {

        String COUNT_USERS_SQL = """
                SELECT COUNT(*) FROM users
                """;

        return jdbcClient.sql(COUNT_USERS_SQL)
                .query(Long.class)
                .single();
    }

}