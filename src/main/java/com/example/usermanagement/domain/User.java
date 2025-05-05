package com.example.usermanagement.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class User {
    private final UUID id;
    private String name;
    private String email;
    private Set<Role> roles = new HashSet<>();

    public User(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    public void assignRole(Role role) {
        this.roles.add(role);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }
}