package com.example.healthyapp.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.View.UserViews.ActuContra;
import com.example.healthyapp.View.DevicesViews.UserDevicesView;
import com.example.healthyapp.View.UserViews.Habitosconfig;
import com.example.healthyapp.View.MainViews.ConfigurationView;
import com.example.healthyapp.View.HabitsViews.TemperatureSecondView;
import com.example.healthyapp.View.UserViews.usuarioconfig;


public class ConfigurationViewModel extends ViewModel {
    private MutableLiveData<Class<? extends AppCompatActivity>> navigationEvent = new MutableLiveData<>();

    public LiveData<Class<? extends AppCompatActivity>> getNavigationEvent() {
        return navigationEvent;
    }

    public void onUserSettingsClicked() {
        navigationEvent.setValue(usuarioconfig.class);
    }

    public void onArrowClicked(){
        navigationEvent.setValue(ConfigurationView.class);
    }
    public void onChangePasswordClicked() {
        navigationEvent.setValue(ActuContra.class);
    }

    public void onDeviceSettingsClicked() {
        navigationEvent.setValue(UserDevicesView.class);
    }

    public void onHabitConfigClicked() {
        navigationEvent.setValue(Habitosconfig.class);
    }

    public void onLogoutClicked() {
        navigationEvent.setValue(TemperatureSecondView.class);
    }
}



