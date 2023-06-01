package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Role;
import com.nosz.projectsem2be.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

}