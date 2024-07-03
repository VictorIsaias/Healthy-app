package com.example.healthyapp.Response;

import com.example.healthyapp.Model.Objects.Tip;

import java.util.List;

public class SensorResponse {
    private String message;
    private String title;
    private String type;
    private Data data;

    private List<Tip> tips;

    public List<Tip> getTips() {
        return tips;
    }

    private String status;

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public SensorResponse(String message) {
        this.message=message;
    }

    public Data getData() {
        return data;
    }

    public class Data{
        private String retained_message;

        private String value;
        private String unit;

        public String getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }

        public String getRetained_message() {
            return retained_message;
        }
    }
}
