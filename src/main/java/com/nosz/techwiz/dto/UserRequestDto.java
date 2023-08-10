package com.nosz.techwiz.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequestDto implements Serializable {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

}
