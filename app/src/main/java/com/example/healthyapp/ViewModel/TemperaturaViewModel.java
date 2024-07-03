package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.Repository.UserRepository;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.Response.TemperaturaResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;
import com.example.healthyapp.View.HabitsViews.TemperatureFirstView;
import com.example.healthyapp.View.HabitsViews.TemperatureSecondView;

public class TemperaturaViewModel extends ViewModel {
    private MutableLiveData<Class<? extends AppCompatActivity>> navigationEvent = new MutableLiveData<>();
    private MutableLiveData<SensorResponse> temperaturaValue = new MutableLiveData<>();
    private MutableLiveData<Boolean> errorOccurred = new MutableLiveData<>();
    private HabitsRepository userRepository;

    public TemperaturaViewModel() {
        userRepository = new HabitsRepository();
    }

    public LiveData<Class<? extends AppCompatActivity>> getNavigationEvent() {
        return navigationEvent;
    }

    public LiveData<SensorResponse> getTemperaturaValue() {
        return temperaturaValue;
    }

    public LiveData<Boolean> getErrorOccurred() {
        return errorOccurred;
    }

    public void onConfigClicked() {
        navigationEvent.setValue(ConfigurationView.class);
    }

    public void onTempClicked() {
        navigationEvent.setValue(TemperatureFirstView.class);
    }

    public void onMedTemp() {
        navigationEvent.setValue(TemperatureSecondView.class);
    }

    public void onArrowClicked() {
        navigationEvent.setValue(ConfigurationView.class);
    }

    @SuppressLint("CheckResult")
    public void obtenerTemperatura(String token) {
        userRepository.getTemperaturaResponse(token)
                .subscribe(response -> {
                    SensorResponse res = (SensorResponse) response;
                    temperaturaValue.setValue(res);
                }, throwable -> {
                    temperaturaValue.setValue(new SensorResponse(throwable.getMessage()));
                });
    }

}
