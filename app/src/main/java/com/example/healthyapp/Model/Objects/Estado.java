package com.example.healthyapp.Model.Objects;

import android.graphics.Color;

public class Estado {
    private Integer color;
    private String texto;
    private String titulo;
    private Double calorias;

    public Estado(Integer color, String titulo, Double calorias,String texto) {
        this.color = color;
        this.texto = texto;
        this.calorias = calorias;
        this.titulo = titulo;
    }

    public Integer getColor() {
        return color;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }
}
