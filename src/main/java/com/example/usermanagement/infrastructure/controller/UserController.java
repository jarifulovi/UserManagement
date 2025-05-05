package com.example.usermanagement.infrastructure.controller;

import com.example.usermanagement.application.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request.name(), request.email());
    }

    @GetMapping("/{id}")
    public UserDetailsResponse getUserDetails(@PathVariable UUID id) {
        return userService.getUserDetails(id);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public String assignRoleToUser(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        userService.assignRoleToUser(userId, roleId);
        return "Role assigned successfully";
    }

    // Request/Response DTOs
    public record CreateUserRequest(
            @NotBlank String name,
            @NotBlank @Email String email
    ) {}

    public record UserDetailsResponse(
            UUID id,
            String name,
            String email,
            Iterable<String> roles // Using Iterable to maintain flexibility
    ) {}
}