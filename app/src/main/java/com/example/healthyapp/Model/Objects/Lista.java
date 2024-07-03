package com.example.healthyapp.Model.Objects;

import java.util.List;

public class Lista {
    private List<String> ingr;

    public List<String> getList() {
        return ingr;
    }

    public Lista(List<String> list) {
        this.ingr = list;
    }
}
