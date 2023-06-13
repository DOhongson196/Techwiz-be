package com.nosz.projectsem2be.dto;

import com.nosz.projectsem2be.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class SignUpFormDto implements Serializable {
    @Email @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String password;
    private Set<String> roles;
}
