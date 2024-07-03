package com.example.healthyapp.Model.DeviceObjects;

import java.io.Serializable;

public class Sensor  implements Serializable {
    public Sensor(Integer id, Integer sensor_type_id, Integer dispositivo_id, Integer activo, String value) {
        this.id = id;
        this.sensor_type_id = sensor_type_id;
        this.dispositivo_id = dispositivo_id;
        this.activo = activo;
        this.value = value;
    }

    private Integer id;
    private Integer sensor_type_id;
    private Integer dispositivo_id;
    private Integer activo;
    private String value;

    private Tipo_dispositivo sensorType;

    public Tipo_dispositivo getSensorType() {
        return sensorType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSensor_type_id() {
        return sensor_type_id;
    }

    public void setSensor_type_id(Integer sensor_type_id) {
        this.sensor_type_id = sensor_type_id;
    }

    public Integer getDispositivo_id() {
        return dispositivo_id;
    }

    public void setDispositivo_id(Integer dispositivo_id) {
        this.dispositivo_id = dispositivo_id;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
