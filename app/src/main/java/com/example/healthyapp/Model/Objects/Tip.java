package com.example.healthyapp.Model.Objects;

public class Tip {
    public Tip(Integer imagen, String mensaje) {
        this.imagen = imagen;
        this.mensaje = mensaje;
    }

    private Integer imagen;
    private String mensaje;

    public Integer getImagen() {
        return imagen;
    }

    public String getMensaje() {
        return mensaje;
    }
}
