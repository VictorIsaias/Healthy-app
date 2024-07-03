package com.example.healthyapp.Model.Objects;

import android.graphics.drawable.Drawable;

public class Habito {
    private String name;
    private String description;
    private Drawable image;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Habito(String titulo, String descripcion, Drawable image) {
        this.name = titulo;
        this.description = descripcion;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
