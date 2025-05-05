package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.application.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid CreateRoleRequest request) {
        UUID roleId = roleService.createRole(request.roleName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RoleResponse(roleId, request.roleName()));
    }

    // Request/Response DTOs
    public record CreateRoleRequest(
            @NotBlank(message = "Role name cannot be empty")
            @Pattern(regexp = "^[A-Z0-9_]{2,30}$", 
                    message = "Role name must be 2-30 characters long and contain only uppercase letters, numbers, and underscores")
            String roleName
    ) {}

    public record RoleResponse(UUID id, String roleName) {}
}