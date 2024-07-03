package com.example.healthyapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.UserObjects.VerificacionUsuario;
import com.example.healthyapp.Response.VerificacionResponse;
import com.example.healthyapp.Repository.UserRepository;

public class VerificacionViewModel extends ViewModel {
    private UserRepository userRepository;
    public VerificacionViewModel() {
        userRepository = UserRepository.getInstance();
    }
    public LiveData<VerificacionResponse> verifyUser(VerificacionUsuario verificacionUsuario) {
        return userRepository.verifyUser(verificacionUsuario);
    }
}
