package com.nosz.projectsem2be.service;

import com.nosz.projectsem2be.entity.Role;
import com.nosz.projectsem2be.entity.RoleName;
import com.nosz.projectsem2be.respository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public Optional<Role> finByName(RoleName name){
        return roleRepository.findByRoleName(name);
    }
}
