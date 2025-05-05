package com.example.usermanagement.application;

import com.example.usermanagement.application.exception.ResourceNotFoundException;
import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    void createRole_WithValidName_ShouldReturnRoleId() {
        // Arrange
        String roleName = "ADMIN";
        Role mockRole = new Role(roleName);
        UUID expectedId = UUID.randomUUID();
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        // Act
        UUID result = roleService.createRole(roleName);

        // Assert
        assertThat(result).isNotNull();
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void createRole_nameInAnyCase() {
        // Arrange
        String roleName = "admin";
        Role mockRole = new Role("ADMIN");
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        // Act
        roleService.createRole(roleName);

        // Assert
        verify(roleRepository).save(any(Role.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void createRole_WithEmptyName_ShouldThrowException(String emptyName) {
        assertThatThrownBy(() -> roleService.createRole(emptyName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Role name cannot be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "Admin@123", "ROLE_WITH_VERY_LONG_NAME_EXCEEDING_LIMIT"})
    void createRole_WithInvalidFormat_ShouldThrowException(String invalidName) {
        assertThatThrownBy(() -> roleService.createRole(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Role name must be 2-30 characters long");
    }

    @Test
    void getRoleById_WithExistingId_ShouldReturnRole() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        Role expectedRole = new Role("ADMIN");
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(expectedRole));

        // Act
        Role result = roleService.getRoleById(roleId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRoleName()).isEqualTo("ADMIN");
    }

    @Test
    void getRoleById_WithNonExistingId_ShouldThrowException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(roleRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roleService.getRoleById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Role not found");
    }
}