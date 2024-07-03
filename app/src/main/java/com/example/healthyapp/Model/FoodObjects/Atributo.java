package com.example.healthyapp.Model.FoodObjects;

import java.io.Serializable;

public class Atributo implements Serializable {
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String label;
    private Double quantity;

    public Atributo(Double quantity) {
        this.quantity = quantity;
    }


    public Atributo(String label, String unit, Double quantity, Double porcentaje) {
        this.unit = unit;
        this.label = label;
        this.quantity = quantity;
        this.porcentaje = porcentaje;
    }
    public Atributo(String label, String unit, Double quantity) {
        this.unit = unit;
        this.label = label;
        this.quantity = quantity;
    }

    public Atributo(String unit, Double quantity) {
        this.unit = unit;
        this.quantity = quantity;
    }

    private Double porcentaje;

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    private String unit;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
