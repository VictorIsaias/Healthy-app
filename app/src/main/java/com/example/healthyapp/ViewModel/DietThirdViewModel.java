package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;
import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.FoodObjects.Receta;
import com.example.healthyapp.Model.HabitsModels.DietThirdModel;
import com.example.healthyapp.Model.Objects.Estado;
import com.example.healthyapp.Repository.HabitsRepository;

import java.util.List;

public class DietThirdViewModel extends ViewModel {

    private final HabitsRepository habitsRepository =new HabitsRepository();

    private MutableLiveData<Estado> estado = new MutableLiveData<>(new Estado(Color.rgb(0,0,0)," ",0.0," "));
    public LiveData<Estado> getEstado() {
        return estado;
    }

    private MutableLiveData<Receta> receta = new MutableLiveData<>(new Receta());
    public LiveData<Receta> getReceta() {
        return receta;
    }
    public void setMensaje(Double cal){
        estado.setValue(DietThirdModel.CrearMensaje(cal));
    }

    @SuppressLint("CheckResult")
    public void setAtributos(List<Ingrediente> alimentos,String token){
        habitsRepository.getDietResponse(alimentos,token)
                .subscribe(response -> {
                    receta.setValue((Receta) response);
                }, throwable -> {
                    receta.setValue(receta.getValue().setMessage(throwable.getMessage()));
                });


    }
}
