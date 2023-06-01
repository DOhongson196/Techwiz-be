package com.nosz.projectsem2be.service;

import com.nosz.projectsem2be.entity.AuthProvider;
import com.nosz.projectsem2be.entity.Role;
import com.nosz.projectsem2be.entity.RoleName;
import com.nosz.projectsem2be.entity.User;
import com.nosz.projectsem2be.exception.UserException;
import com.nosz.projectsem2be.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void checkEnable(String email){
       User user = userRepository.findUserByEmail(email);
       if(user == null){
           throw new UserException("Email does not exist");
       }
       if(!user.getEnabled()){
           throw new UserException("Account is not activated");
       }
    }

    public User getUser(String email){
        return userRepository.findUserByEmail(email);
    }

    public Boolean existByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public int enableUser(String email){
        return userRepository.enableUser(email);
    }

    public void createNewUserOAuthLogin(String email, String name, AuthProvider authProvider) {
        User user = new User();
        user.setEmail(email);
        user.setEnabled(true);
        user.setName(name);
        user.setAuthProvider(authProvider);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.finByName(RoleName.USER).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
