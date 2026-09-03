package com.sashia.ecommerce.platform.security;

import com.sashia.ecommerce.platform.security.user.SashiaUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SashiaUserServiceImpl implements SashiaUserService {

    private final SashiaUserRepository sashiaUserRepository;

    public SashiaUserServiceImpl(SashiaUserRepository sashiaUserRepository) {
        this.sashiaUserRepository = sashiaUserRepository;
    }

    @Override
    @Cacheable(cacheNames = "user-auth", unless = "#result == null")
    public Optional<SashiaUser> findByPhone(String phone) {
        return sashiaUserRepository.findUserByPhone(phone);
    }

}
