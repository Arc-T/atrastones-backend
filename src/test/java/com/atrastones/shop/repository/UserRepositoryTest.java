package com.atrastones.shop.repository;

import com.atrastones.shop.dto.UserDTO;
import com.atrastones.shop.model.entity.User;
import com.atrastones.shop.model.repository.contract.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @Mock
    private Logger log;

    @Autowired
    private UserRepository userRepository;

    private UserDTO userDTO;
    private KeyHolder keyHolder;

    @BeforeEach
    void setUp() {
        UserDTO user = UserDTO.builder()
                .phone("361629708")
                .build();
    }

    @Test
    void testFindAdminByPhone() {
        // Given: existing Admin user phone
        String adminPhone = "09123456789"; // replace with actual phone

        // When
        User adminUser = userRepository.getByPhone(adminPhone);

        log.debug("adminUser={}", adminUser);
    }

//    @Test
//    void insertUser_SuccessfulInsertion_ReturnsGeneratedId() {
//        // Arrange
//        long expectedId = 1L;
//        when(jdbcClient.sql(anyString()).param(anyString(), anyString()).update(any(KeyHolder.class)))
//                .thenAnswer(invocation -> {
//                    KeyHolder kh = invocation.getArgument(2);
//                    kh.getKeyList().add(Map.of("id", expectedId));
//                    return 1;
//                });
//
//        // Act
//        long result = userService.insertUser(userDTO);
//
//        // Assert
//        assertEquals(expectedId, result);
//        verify(jdbcClient).sql("INSERT INTO `users` (`phone`) VALUES (:phone)")
//                .param("phone", userDTO.getFirsName())
//                .update(keyHolder);
//        verify(log).debug("Inserted user with phone: {}, generated ID: {}",
//                userDTO.getFirsName(), expectedId);
//    }
}
