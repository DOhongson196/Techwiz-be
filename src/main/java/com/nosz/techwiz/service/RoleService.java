package com.nosz.techwiz.service;

import com.nosz.techwiz.entity.Role;
import com.nosz.techwiz.entity.Enum.RoleName;
import com.nosz.techwiz.respository.RoleRepository;
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
