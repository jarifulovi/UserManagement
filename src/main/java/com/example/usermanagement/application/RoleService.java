package com.example.usermanagement.application;

import com.example.usermanagement.application.exception.ResourceNotFoundException;
import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UUID createRole(String roleName) {
        validateRoleName(roleName);
        Role role = new Role(roleName.trim().toUpperCase());
        return roleRepository.save(role).getId();
    }

    public Role getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));
    }

    private void validateRoleName(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be empty");
        }
        if (!roleName.matches("^[A-Za-z0-9_]{2,30}$")) {
            throw new IllegalArgumentException("Role name must be 2-30 characters long and contain only uppercase letters, numbers, and underscores");
        }
    }
}