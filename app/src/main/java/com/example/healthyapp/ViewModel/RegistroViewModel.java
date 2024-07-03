package com.example.healthyapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.UserObjects.UsuarioRegistro;
import com.example.healthyapp.Response.RegistroResponse;
import com.example.healthyapp.Repository.UserRepository;

public class RegistroViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<RegistroResponse> registroLiveData;
    private final MutableLiveData<String> mensaje=new MutableLiveData<>();
    public RegistroViewModel() {
        userRepository = UserRepository.getInstance();
        registroLiveData = new MutableLiveData<>();
    }
    public LiveData<RegistroResponse> getRegistroLiveData() {
        return registroLiveData;
    }
    public LiveData<String> getMensaje() {
        return mensaje;
    }
    public void registrarUsuario(UsuarioRegistro usuarioRegistro) {
        userRepository.registerUser(usuarioRegistro).observeForever(registroResponse -> {
            if(registroResponse.getData()==null){
                mensaje.setValue(registroResponse.getMessage());
            }else{
                registroLiveData.setValue(registroResponse);

            }

        });
    }
}
