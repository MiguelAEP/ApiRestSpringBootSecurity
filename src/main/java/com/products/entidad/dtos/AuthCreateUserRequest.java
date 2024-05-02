package com.products.entidad.dtos;

import java.util.List;

public class AuthCreateUserRequest {
    private String username;
    private String password;
    private List<String> roleListName;

    public AuthCreateUserRequest(String username, String password, List<String> roleListName) {
        this.username = username;
        this.password = password;
        this.roleListName = roleListName;
    }

    public AuthCreateUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoleListName() {
        return roleListName;
    }

    public void setRoleListName(List<String> roleListName) {
        this.roleListName = roleListName;
    }
}
