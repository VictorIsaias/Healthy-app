package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Response.LoginResponse;
import com.example.healthyapp.Repository.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository;
    public LoginViewModel() {
        userRepository = UserRepository.getInstance();
    }
    private MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
    public LiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    @SuppressLint("CheckResult")
    public void login(String email, String password) throws InterruptedException {
        userRepository.getLoginResponse(email, password)
                .subscribe(response -> {
                    loginResponse.setValue((LoginResponse) response);
                }, throwable -> {
                    loginResponse.setValue(new LoginResponse(throwable));
                });

    }
}