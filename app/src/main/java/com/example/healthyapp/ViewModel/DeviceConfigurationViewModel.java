package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.UserDevicesResponse;

import java.util.List;

public class DeviceConfigurationViewModel extends ViewModel {
    private final DevicesRepository repository =new DevicesRepository();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    @SuppressLint("CheckResult")
    public void eliminateDevice(Integer id,String token){
        repository.eliminateDevice(id,token)
                .subscribe(response -> {
                    mensaje.setValue((String) response);

                }, throwable -> {
                    mensaje.setValue( throwable.getMessage());
                });
    }
    @SuppressLint("CheckResult")
    public void updateDevice(Integer id,String nombre,String token){
        repository.updateDevice(id,nombre,token)
                .subscribe(response -> {
                    mensaje.setValue((String) response);

                }, throwable -> {
                    mensaje.setValue( throwable.getMessage());
                });
    }

    private MutableLiveData<User> user = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return user;
    }
    @SuppressLint("CheckResult")
    public void getUpdatedUser(String token, User us){
        repository.getDevicesResponse(token,us)
                .subscribe(response -> {
                    UserDevicesResponse res=(UserDevicesResponse) response;
                    user.setValue( res.getData());

                }, throwable -> {
                    user.setValue(new User());
                });

    }

}
