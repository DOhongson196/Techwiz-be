package com.nosz.techwiz.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPassDto implements Serializable {
    @Email
    private String email;
    @NotNull
    private String token;
    @NotNull
    private String password;
}