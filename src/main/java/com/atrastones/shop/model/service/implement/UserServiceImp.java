package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.UserDTO;
import com.atrastones.shop.model.entity.User;
import com.atrastones.shop.model.repository.contract.UserRepository;
import com.atrastones.shop.model.service.contract.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> get(Long id) {
        return Optional.empty();
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

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirsName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .phone(user.getPhone())
                .email(user.getPhone())
                .description(user.getDescription())
                .gender(user.getGender())
                .groupId(user.getUserGroup().getId())
                .build();
    }

}