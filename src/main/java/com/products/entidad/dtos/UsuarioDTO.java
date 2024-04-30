package com.products.entidad.dtos;

import com.products.entidad.authEntidades.Rol;

import java.util.List;

public class UsuarioDTO {

    private String username;
    private String password;
    private List<Rol> rol;

    public UsuarioDTO(String username, String password, List<Rol> rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public UsuarioDTO() {
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

    public List<Rol> getRol() {
        return rol;
    }

    public void setRol(List<Rol> rol) {
        this.rol = rol;
    }
}
