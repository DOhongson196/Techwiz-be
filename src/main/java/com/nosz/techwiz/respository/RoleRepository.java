package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.Role;
import com.nosz.techwiz.entity.Enum.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

}