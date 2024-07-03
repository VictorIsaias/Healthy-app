package com.example.healthyapp.Response;

public class RitmoResponse {

    private String message;
    private String title;
    private String type;
    private PasosResponse.Data data;

    public PasosResponse.Data getData() {
        return data;
    }

    public class Data{
        private String retained_message;
        private String unit;

        public String getUnit() {
            return unit;
        }

        public String getRetained_message() {
            return retained_message;
        }
    }
}
