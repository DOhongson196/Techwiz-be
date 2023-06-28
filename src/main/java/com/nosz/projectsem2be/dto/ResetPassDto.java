package com.nosz.projectsem2be.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPassDto implements Serializable {
    @Email
    private String email;

    private String token;

    private String password;
}
