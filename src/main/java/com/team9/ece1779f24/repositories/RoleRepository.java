package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.enums.AppRole;
import com.team9.ece1779f24.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
