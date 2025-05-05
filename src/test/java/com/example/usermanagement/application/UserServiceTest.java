package com.example.usermanagement.application;

import com.example.usermanagement.domain.Role;
import com.example.usermanagement.domain.User;
import com.example.usermanagement.infrastructure.controller.UserController.UserDetailsResponse;
import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.application.interfaces.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, roleRepository);
    }

    @Test
    void createUser_ShouldReturnUserId() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        User mockUser = new User(name, email);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        UUID result = userService.createUser(name, email);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserDetails_ShouldReturnUserDetails() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User mockUser = new User("John Doe", "john.doe@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        UserDetailsResponse response = userService.getUserDetails(userId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("John Doe");
        assertThat(response.email()).isEqualTo("john.doe@example.com");
        assertThat(response.roles()).isNotNull();
    }

    @Test
    void assignRoleToUser_ShouldAssignRole() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User mockUser = new User("John Doe", "john.doe@example.com");
        Role mockRole = new Role("ADMIN");
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));

        // Act
        userService.assignRoleToUser(userId, roleId);

        // Assert
        verify(userRepository).findById(userId);
        verify(roleRepository).findById(roleId);
        verify(userRepository).save(any(User.class));
    }
}