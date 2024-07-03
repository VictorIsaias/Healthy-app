package com.example.healthyapp.Model.DeviceObjects;

import java.io.Serializable;
import java.util.List;

public class Dispositivo implements Serializable {

    private Integer id;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    private Tipo_dispositivo tipoDispositivo;

    public Tipo_dispositivo getTipo_dispositivo() {
        return tipoDispositivo;
    }

    public Dispositivo(Integer id, Integer tipo_dispositivo_id, Integer id_usuario, List<Sensor> sensores) {
        this.id = id;
        this.tipo_dispositivo_id = tipo_dispositivo_id;
        this.id_usuario = id_usuario;
        this.sensores = sensores;
    }

    private Integer tipo_dispositivo_id;
    private Integer id_usuario;
    private List<Sensor> sensores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipo_dispositivo_id() {
        return tipo_dispositivo_id;
    }

    public void setTipo_dispositivo_id(Integer tipo_dispositivo_id) {
        this.tipo_dispositivo_id = tipo_dispositivo_id;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }
}
