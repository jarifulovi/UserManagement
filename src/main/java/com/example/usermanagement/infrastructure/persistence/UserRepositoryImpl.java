package com.example.usermanagement.infrastructure.persistence;

import com.example.usermanagement.application.interfaces.UserRepository;
import com.example.usermanagement.domain.User;
import com.example.usermanagement.domain.Role;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpaRepository;

     public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
     }

    @Override
    public User save(User user) {
        UserJpaEntity entity = jpaRepository.findById(user.getId())
            .orElse(new UserJpaEntity(user.getId(), user.getName(), user.getEmail()));
        
        // Update the entity with current values
        entity.getRoles().clear();
        entity.getRoles().addAll(
            user.getRoles().stream()
                .map(role -> new RoleJpaEntity(role.getId(), role.getRoleName()))
                .collect(Collectors.toSet())
        );
        
        jpaRepository.save(entity);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(entity -> {
                User user = new User(entity.getId(),entity.getName(), entity.getEmail());
                entity.getRoles().forEach(roleEntity ->
                    user.assignRole(new Role(roleEntity.getId(), roleEntity.getRoleName()))
                );
                return user;
            });
    }
}