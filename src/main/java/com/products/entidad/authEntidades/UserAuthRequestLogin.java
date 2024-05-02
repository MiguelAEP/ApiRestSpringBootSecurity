package com.products.entidad.authEntidades;


import jakarta.validation.constraints.NotBlank;



public class UserAuthRequestLogin {
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String password;

    public UserAuthRequestLogin() {
    }

    public UserAuthRequestLogin(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
