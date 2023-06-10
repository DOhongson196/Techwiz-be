package com.nosz.projectsem2be.dto;

import com.nosz.projectsem2be.entity.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SignUpFormDto implements Serializable {
    @Email
    private String email;
    private String phone;
    private String password;
    private Set<String> roles;
}
