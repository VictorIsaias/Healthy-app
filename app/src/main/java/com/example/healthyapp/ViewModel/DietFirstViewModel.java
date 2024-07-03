package com.example.healthyapp.ViewModel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.View.HabitsViews.DietSecondView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DietFirstViewModel extends ViewModel {


    private final HabitsRepository habitsRepository =new HabitsRepository();
    private MutableLiveData<List<Ingrediente>> ingredientes = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Ingrediente>> getIngredientes() {
        return ingredientes;
    }

    private MutableLiveData<Double> calorias = new MutableLiveData<>();
    public LiveData<Double> getCalorias() {
        return calorias;
    }

    public DietFirstViewModel() {

        calorias = calorias;
    }

    public void anadirIngALista(Activity context) {

        Intent intent = new Intent(context.getApplicationContext(), DietSecondView.class);
        intent.putExtra("lista",(Serializable) ingredientes.getValue());
        context.startActivity(intent);
        context.finish();

    }
    public void anadirIngALista(List<Ingrediente> lista) {

            ingredientes.setValue(lista);


    }
}
