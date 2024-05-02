package com.products.entidad.dtos;


public class ResponseLogin {
    private String nombreUsuario;
    private String jwt;
    private boolean status;


    public ResponseLogin(String nombreUsuario, String jwt, boolean status) {
        this.nombreUsuario = nombreUsuario;
        this.jwt = jwt;
        this.status = status;
    }

    public ResponseLogin() {
    }


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
