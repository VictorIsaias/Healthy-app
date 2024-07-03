package com.example.healthyapp.Model.Objects;
import com.google.gson.Gson;

public class ObjectSerializer {
    private static final Gson gson = new Gson();

    public static String serialize(Object object) {
        return gson.toJson(object);
    }

    public static <T> T deserialize(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}