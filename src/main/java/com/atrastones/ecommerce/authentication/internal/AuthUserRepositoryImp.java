package com.atrastones.ecommerce.authentication.internal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class AuthUserRepositoryImp implements AuthUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AuthUser> findByPhone(String phone) {
        return Optional.ofNullable(
                entityManager.createQuery("""
                                            SELECT au FROM AuthUser au
                                                 JOIN FETCH au.userGroup ug
                                                 JOIN FETCH ug.roles r
                                                 JOIN FETCH r.permissions
                                                 WHERE au.phone = :phone
                                """, AuthUser.class)
                        .setParameter("phone", phone)
                        .getSingleResultOrNull());
    }

}
