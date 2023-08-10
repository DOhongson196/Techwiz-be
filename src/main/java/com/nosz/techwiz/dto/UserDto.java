package com.nosz.techwiz.dto;

import com.nosz.techwiz.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String address;
    private Set<Role> roles;
}
