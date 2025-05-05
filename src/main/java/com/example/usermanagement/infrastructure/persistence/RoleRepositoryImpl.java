package com.example.usermanagement.infrastructure.persistence;

import com.example.usermanagement.application.interfaces.RoleRepository;
import com.example.usermanagement.domain.Role;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpaRepository jpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = new RoleJpaEntity(role.getId(), role.getRoleName());
        jpaRepository.save(entity);
        return new Role(entity.getId(), entity.getRoleName());
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> new Role(entity.getId(), entity.getRoleName()));
    }
}