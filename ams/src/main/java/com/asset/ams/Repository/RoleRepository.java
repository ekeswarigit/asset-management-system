package com.asset.ams.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asset.ams.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
