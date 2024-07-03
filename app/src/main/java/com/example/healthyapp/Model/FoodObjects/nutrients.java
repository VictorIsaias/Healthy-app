package com.example.healthyapp.Model.FoodObjects;

import java.io.Serializable;

public class nutrients implements Serializable {
    private Double ENERC_KCAL;
    private Double PROCNT;
    private Double FAT;
    private Double CHOCDF;
    private Double FIBTG;

    public Double getENERC_KCAL() {
        return ENERC_KCAL;
    }

    public Double getPROCNT() {
        return PROCNT;
    }

    public Double getFAT() {
        return FAT;
    }

    public Double getCHOCDF() {
        return CHOCDF;
    }

    public Double getFIBTG() {
        return FIBTG;
    }


}
