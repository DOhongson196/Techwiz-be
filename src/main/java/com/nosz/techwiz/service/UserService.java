package com.nosz.techwiz.service;



import com.nosz.techwiz.entity.User;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
           throw new CustomException("Email does not exist");
       }
       if(!user.getEnabled()){
           throw new CustomException("Account is not activated");
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

    public void updatePassword(String email, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        var user = userRepository.findUserByEmail(email);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}
