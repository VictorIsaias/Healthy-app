package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.DeviceObjects.Tipo_dispositivo;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.DeviceTypeResponse;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.Response.UserDevicesResponse;

import java.util.List;

public class NewDeviceViewModel extends ViewModel {
    private final DevicesRepository repository =new DevicesRepository();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public LiveData<String> getMensaje() {
        return mensaje;
    }
    private MutableLiveData<List<String>> tipos_dispositivo = new MutableLiveData<>();

    public LiveData<List<String>> getTiposDispositivo() {
        return tipos_dispositivo;
    }



    @SuppressLint("CheckResult")
    public void setTipos_dispositivo(String token) {
        repository.getDeviceTypes(token)
                .subscribe(response -> {
                    List<String> res = (List<String>) response;
                    tipos_dispositivo.setValue(res);


                }, throwable -> {
                    mensaje.setValue(throwable.getMessage());
                });

    }

    @SuppressLint("CheckResult")
    public void setNewDevice(String tipoDispositivo, String nombre, String token, User user){
        repository.setNewDevice( tipoDispositivo, nombre,token)
                .subscribe(response -> {
                    mensaje.setValue((String) response);
                    repository.setNewConfigurations(tipoDispositivo,token,user);

                }, throwable -> {
                    mensaje.setValue(throwable.getMessage());
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
