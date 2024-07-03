package com.example.healthyapp.Response;

public class PasosResponse {

    private String message;
    private String title;
    private String type;
    private Data data;

    public Data getData() {
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
