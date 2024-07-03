package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.HabitsModels.DietSecondModel;
import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.Response.SensorResponse;

public class ControlAlcoholViewModel extends ViewModel {
    private final HabitsRepository habitsRepository =new HabitsRepository();

    private MutableLiveData<SensorResponse> alcoholResponse = new MutableLiveData<>();
    public LiveData<SensorResponse> getAlcoholResponse() {
        return alcoholResponse;
    }


    @SuppressLint("CheckResult")
    public void medirAlcohol(String token){
        habitsRepository.getAlcoholResponse( token)
                .subscribe(response -> {
                    SensorResponse res = (SensorResponse) response;
                    alcoholResponse.setValue(res);
                }, throwable -> {
                    alcoholResponse.setValue(new SensorResponse(throwable.getMessage()));
                });

    }
}
