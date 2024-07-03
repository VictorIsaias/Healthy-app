package com.example.healthyapp.Response;

public class ScreenSelectorResponse {
    private String message;
    private String title;
    private String type;
    private PasosResponse.Data data;

    public PasosResponse.Data getData() {
        return data;
    }

    public class Data{
        private String retained_message;
        public String getRetained_message() {
            return retained_message;
        }
    }
}
