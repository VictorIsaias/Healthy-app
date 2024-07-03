package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.Habito;
import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.Repository.MainRepository;
import com.example.healthyapp.Response.FoodResponse;

import java.util.ArrayList;
import java.util.List;

public class HabitDashboardViewModel extends ViewModel {
    private final MainRepository repository =new MainRepository();
    private MutableLiveData<List<Habito>> habitos = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Habito>> getHabitos() {
        return habitos;
    }

    @SuppressLint("CheckResult")
    public void actualizarLista(String token) {
        repository.getHabitsResponse(token)
                .subscribe(response -> {
                    habitos.setValue((List<Habito>) response);
                }, throwable -> {
                    habitos.setValue(new ArrayList<>());
                });


    }

}
