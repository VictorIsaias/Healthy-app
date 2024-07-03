package com.example.healthyapp.Model.FoodObjects;

import java.io.Serializable;

public class food implements Serializable {
    private String label;
    private String knownAs;
    private String foodId;

    private nutrients nutrients;

    private String category;
    private String categoryLabel;
    private String image;

    public String getCategory() {
        return category;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public String getImage() {
        return image;
    }

    public String getLabel() {
        return label;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public String getFoodId() {
        return foodId;
    }

    public com.example.healthyapp.Model.FoodObjects.nutrients getNutrients() {
        return nutrients;
    }

    private Atributo peso;

    public Atributo getPeso() {
        return peso;
    }

    public void setPeso(Atributo peso) {
        peso.setUnit("g");
        this.peso = peso;
    }
}
