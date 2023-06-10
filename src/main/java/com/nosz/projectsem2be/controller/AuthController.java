package com.nosz.projectsem2be.controller;

import com.nosz.projectsem2be.dto.SignUpFormDto;
import com.nosz.projectsem2be.dto.UserRequestDto;
import com.nosz.projectsem2be.dto.UserResponseDto;
import com.nosz.projectsem2be.email.EmailService;
import com.nosz.projectsem2be.entity.ConfirmToken;
import com.nosz.projectsem2be.entity.Role;
import com.nosz.projectsem2be.entity.RoleName;
import com.nosz.projectsem2be.entity.User;
import com.nosz.projectsem2be.exception.UserException;
import com.nosz.projectsem2be.security.UserPrinciple;
import com.nosz.projectsem2be.security.jwt.JwtProvider;
import com.nosz.projectsem2be.service.ConfirmTokenService;
import com.nosz.projectsem2be.service.MapValidationErrorService;
import com.nosz.projectsem2be.service.RoleService;
import com.nosz.projectsem2be.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ConfirmTokenService confirmTokenService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    RoleService roleService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpFormDto signUpFormDto, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);

        if (responseEntity != null) {
            return responseEntity;
        }
        if (userService.existByEmail(signUpFormDto.getEmail())) {
            throw new UserException("Email is existed! pls try again");
        }
        User user = new User();
        String[] ignoreFields = new String[]{"password", "roles"};
        BeanUtils.copyProperties(signUpFormDto, user, ignoreFields);
        user.setPassword(passwordEncoder.encode(signUpFormDto.getPassword()));
        Set<String> strRoles = signUpFormDto.getRoles();
        if (strRoles != null) {
            Set<Role> roles = new HashSet<>();

            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN" -> {
                        Role adminRole = roleService.finByName(RoleName.ROLE_ADMIN).orElseThrow(
                                () -> new RuntimeException("Role not found")
                        );
                        roles.add(adminRole);
                    }
                    case "EDIT" -> {
                        Role editRole = roleService.finByName(RoleName.ROLE_EDITOR).orElseThrow(
                                () -> new RuntimeException("Role not found"));
                        roles.add(editRole);
                    }
                    default -> {
                        Role userRole = roleService.finByName(RoleName.ROLE_CUSTOMER).orElseThrow(
                                () -> new RuntimeException("Role not found")
                        );
                        roles.add(userRole);
                    }
                }
            });
            user.setRoles(roles);
        } else {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleService.finByName(RoleName.ROLE_CUSTOMER).orElseThrow(
                    () -> new RuntimeException("Role not found")
            );
            roles.add(userRole);
            user.setRoles(roles);
        }

        userService.saveUser(user);

        String token = UUID.randomUUID().toString();
        ConfirmToken confirmToken = new ConfirmToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), user);
        confirmTokenService.saveConfirmToken(confirmToken);
        String link = "http://localhost:8181/api/v1/auth/confirm?token=" + token;

//        emailService.sendMail(user.getEmail(), confirmTokenService.buildEmail(user.getEmail(), link));
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.checkEnable(userRequestDto.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.createToken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

            return ResponseEntity.ok(new UserResponseDto(token, userPrinciple.getAuthorities()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return confirmTokenService.checkToken(token);
    }

    @GetMapping(path = "reset")
    public ResponseEntity<?> resetConfirmToken(@RequestParam("email") String email) {
        User user = userService.getUser(email);
        if (user.getEnabled()) {
            return new ResponseEntity<>("Email already confirmed", HttpStatus.OK);
        }
        String token = UUID.randomUUID().toString();
        ConfirmToken confirmToken = new ConfirmToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), user);
        confirmTokenService.deleteOldToken(email);
        confirmTokenService.saveConfirmToken(confirmToken);
        String link = "http://localhost:8181/api/v1/auth/confirm?token=" + token;

//        emailService.sendMail(email, confirmTokenService.buildEmail(email, link));
        return new ResponseEntity<>("Refresh success", HttpStatus.OK);
    }



}