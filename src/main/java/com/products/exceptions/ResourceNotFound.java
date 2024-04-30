package com.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{

    private String nombreRecurso;
    private String nombreCampo;
    private Integer valorCampo;

    public ResourceNotFound(String nombreRecurso, String nombreCampo, Integer valorCampo) {
        super(String.format("%s No encontrado con : %s : '%s'",nombreRecurso,nombreCampo,valorCampo));
        this.nombreRecurso = nombreRecurso;
        this.nombreCampo = nombreCampo;
        this.valorCampo = valorCampo;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public Integer getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(Integer valorCampo) {
        this.valorCampo = valorCampo;
    }
}
