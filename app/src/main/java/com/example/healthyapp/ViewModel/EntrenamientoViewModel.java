package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.Response.DistanciaResponse;
import com.example.healthyapp.Response.PasosResponse;
import com.example.healthyapp.Response.RitmoResponse;

public class EntrenamientoViewModel extends ViewModel {

    private final HabitsRepository habitsRepository = new HabitsRepository();
    private MutableLiveData<PasosResponse> pasosResponse = new MutableLiveData<>();
    private MutableLiveData<RitmoResponse> ritmoResponse = new MutableLiveData<>();
    private MutableLiveData<DistanciaResponse> distanciaResponse = new MutableLiveData<>();
    public LiveData<PasosResponse> getPasosResponse() {
        return pasosResponse;
    }
    public LiveData<RitmoResponse> getRitmoResponse() {
        return ritmoResponse;
    }
    public LiveData<DistanciaResponse> getDistanciaResponse() {
        return distanciaResponse;
    }

    @SuppressLint("CheckResult")
    public void actualizarPasos(String tokenAuth) throws InterruptedException {
        habitsRepository.getPasosResponse(tokenAuth)
                .subscribe(response -> {
                    pasosResponse.setValue((PasosResponse) response);
                }, throwable -> {
                    pasosResponse.setValue(new PasosResponse());
                });
    }

    @SuppressLint("CheckResult")
    public void actualizarRitmo(String tokenAuth) throws InterruptedException {
        habitsRepository.getRitmoResponse(tokenAuth)
                .subscribe(response -> {
                    ritmoResponse.setValue((RitmoResponse) response);
                }, throwable -> {
                    ritmoResponse.setValue(new RitmoResponse());
                });
    }

    @SuppressLint("CheckResult")
    public void actualizarDistancia(String tokenAuth) throws InterruptedException {
        habitsRepository.getDistanciaResponse(tokenAuth)
                .subscribe(response -> {
                    distanciaResponse.setValue((DistanciaResponse) response);
                }, throwable -> {
                    distanciaResponse.setValue(new DistanciaResponse());
                });
    }
}
