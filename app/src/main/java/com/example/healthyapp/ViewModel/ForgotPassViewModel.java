package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Repository.UserRepository;

import java.util.List;

public class ForgotPassViewModel extends ViewModel {
    private final UserRepository repository =new UserRepository();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public LiveData<String> getMensaje() {
        return mensaje;
    }


    private MutableLiveData<String> mensaje2 = new MutableLiveData<>();

    public LiveData<String> getMensaje2() {
        return mensaje2;
    }


    @SuppressLint("CheckResult")
    public void sendMail(String token,String email) {
        repository.sendMail(token,email)
                .subscribe(response -> {
                    mensaje.setValue((String) response);


                }, throwable -> {
                    mensaje.setValue(throwable.getMessage());
                });

    }

    @SuppressLint("CheckResult")
    public void verifycode(String token,String email,String verificationCode,String newPassword) {
        repository.recoverPassword(token,email,verificationCode,newPassword)
                .subscribe(response -> {
                    mensaje2.setValue((String) response);


                }, throwable -> {
                    mensaje2.setValue(throwable.getMessage());
                });

    }
}
