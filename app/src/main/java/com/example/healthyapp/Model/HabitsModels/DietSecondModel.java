package com.example.healthyapp.Model.HabitsModels;

import com.example.healthyapp.Model.FoodObjects.food;

import java.util.List;

public class DietSecondModel {
    private List<food> alimentos;

    public DietSecondModel setAlimentos(List<food> alimentos) {
        this.alimentos = alimentos;
        return this;
    }

    public List<food> getAlimentos() {
        return alimentos;
    }
    private String peso;

    public DietSecondModel setPeso(String peso) {
        this.peso = peso;
        return this;
    }

    public String getPeso() {
        return peso;
    }
    private String message;

    public DietSecondModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DietSecondModel(List<food> alimentos, String peso) {
        this.alimentos = alimentos;
        this.peso = peso;
    }
}
