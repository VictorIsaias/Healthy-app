package com.example.healthyapp.Model.FoodObjects;

import java.util.List;

public class Receta {

    private List<Atributo> atributos;
    private List<Ingrediente> ingredientes;
    private String message;

    private Double calorias;

    private Double peso;

    public Double getCalorias() {
        return calorias;
    }

    public Double getPeso() {
        return peso;
    }

    public Receta() {

    }

    public Receta setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Receta(List<Atributo> atributos, List<Ingrediente> ingredientes,Double calorias,Double peso) {
        this.atributos = atributos;
        this.ingredientes = ingredientes;

        this.peso = peso;
        this.calorias = calorias;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
