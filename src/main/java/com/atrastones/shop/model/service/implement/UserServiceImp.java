package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.UserDTO;
import com.atrastones.shop.model.repository.contract.UserRepository;
import com.atrastones.shop.model.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public UserDTO loadByPhone(String phone) {
        return UserDTO.toDTO(
                userRepository.getByPhone(phone)
                        .orElseThrow(() -> new UsernameNotFoundException(phone)
                        )
        );
    }

    @Override
    public Optional<UserDTO> getByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public long create(UserDTO user) {
        return 0;
    }

    @Override
    public long update(Long id, UserDTO user) {
        return 0;
    }

    @Override
    public Optional<UserDTO> getUserProfileInfo(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existByPhone(phone);
    }

}