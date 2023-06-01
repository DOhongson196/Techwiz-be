package com.nosz.projectsem2be.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nosz.projectsem2be.entity.Role;
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
