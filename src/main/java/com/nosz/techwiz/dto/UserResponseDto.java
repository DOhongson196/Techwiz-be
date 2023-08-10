package com.nosz.techwiz.dto;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class UserResponseDto implements Serializable {
    private String accessToken;
    private String type = "Bearer";
    private Collection<? extends GrantedAuthority> roles;

    public UserResponseDto(String accessToken,Collection<? extends GrantedAuthority> roles){
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}
