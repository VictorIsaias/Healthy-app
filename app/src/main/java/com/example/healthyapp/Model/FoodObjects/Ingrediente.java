package com.example.healthyapp.Model.FoodObjects;

import java.io.Serializable;

public class Ingrediente implements Serializable {
    private food comida;
    private Atributo proteinas;
    private Atributo calorias;
    private Atributo sodio;

    public food getComida() {
        return comida;
    }

    public void setComida(food comida) {
        this.comida = comida;
    }

    public Atributo getProteinas() {
        return proteinas;
    }

    public void setProteinas(Atributo proteina) {
        proteina.setUnit("g Prot.");
        this.proteinas = proteina;
    }

    public Atributo getCalorias() {
        return calorias;
    }

    public Ingrediente() {
    }

    public void setCalorias(Atributo calorias) {
        calorias.setUnit("Cal.");
        this.calorias = calorias;
    }

    public Atributo getSodio() {
        return sodio;
    }

    public Ingrediente(food comida, Atributo proteinas, Atributo calorias, Atributo sodio) {
        this.comida = comida;
        this.proteinas = proteinas;
        this.calorias = calorias;
        this.sodio = sodio;
    }

    public void setSodio(Atributo sodio) {
        sodio.setUnit("mg Na");
        this.sodio = sodio;
    }
}
