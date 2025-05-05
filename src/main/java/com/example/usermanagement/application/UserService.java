package com.example.usermanagement.application;

import com.example.usermanagement.application.exception.ResourceNotFoundException;
import com.example.usermanagement.domain.User;
import com.example.usermanagement.domain.Role;
import com.example.usermanagement.application.interfaces.UserRepository;
import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.infrastructure.controller.UserController.UserDetailsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UUID createUser(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        User user = new User(name.trim(), email.trim());
        return userRepository.save(user).getId();
    }

    public UserDetailsResponse getUserDetails(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return new UserDetailsResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList())
        );
    }

    @Transactional
    public void assignRoleToUser(UUID userId, UUID roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        user.assignRole(role);
        userRepository.save(user);
    }
}