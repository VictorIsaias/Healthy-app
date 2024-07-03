package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.UserDevicesResponse;

public class UserDevicesViewModel extends ViewModel {
    private final DevicesRepository repository =new DevicesRepository();

    private MutableLiveData<UserDevicesResponse> dispositivos = new MutableLiveData<>();
    public LiveData<UserDevicesResponse> getDispositivos() {
        return dispositivos;
    }

    @SuppressLint("CheckResult")
    public void setDispositivos(String token, User user){
        repository.getDevicesResponse(token,user)
                .subscribe(response -> {

                   dispositivos.setValue((UserDevicesResponse) response);
                }, throwable -> {
                    dispositivos.setValue(new UserDevicesResponse(throwable.getMessage()));
                });

    }
}
