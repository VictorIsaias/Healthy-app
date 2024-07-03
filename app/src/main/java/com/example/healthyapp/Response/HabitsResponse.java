package com.example.healthyapp.Response;

import com.example.healthyapp.Model.Objects.Habito;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HabitsResponse {
    @SerializedName("data")
    private List<Habito> data;

    public List<Habito> getData() {
        return data;
    }


}
