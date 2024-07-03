package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.HabitsModels.DietSecondModel;
import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.Response.FoodResponse;
import com.example.healthyapp.Response.SensorResponse;

import java.util.ArrayList;

public class DietSecondViewModel extends ViewModel {

    private final HabitsRepository habitsRepository =new HabitsRepository();

    private MutableLiveData<DietSecondModel> modelo = new MutableLiveData<>();
    public LiveData<DietSecondModel> getModelo() {
        return modelo;
    }

    public DietSecondViewModel() {
        modelo.setValue(new DietSecondModel(new ArrayList<>(),"0.0 g"));

    }

    @SuppressLint("CheckResult")
    public void actualizarLista(String nombrealimento,String token) {
        habitsRepository.getFoodResponse(nombrealimento,token)
                .subscribe(response -> {
                    FoodResponse res = (FoodResponse) response;
                    modelo.setValue(modelo.getValue().setAlimentos(res.getFoodList()));
                }, throwable -> {
                    modelo.setValue(modelo.getValue().setMessage(throwable.getMessage()));
                });


    }

    @SuppressLint("CheckResult")
    public void actualizarPeso(String token) {
        habitsRepository.getPesoResponse(token)
                .subscribe(response -> {
                    SensorResponse res = (SensorResponse) response;
                    modelo.setValue(modelo.getValue().setPeso(res.getData().getRetained_message()+" "+res.getData().getUnit()));
                }, throwable -> {
                    modelo.setValue(modelo.getValue().setMessage(throwable.getMessage()));
                });


    }

}
