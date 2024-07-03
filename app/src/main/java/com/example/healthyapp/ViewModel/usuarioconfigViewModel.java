package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Repository.UserRepository;
import com.example.healthyapp.Response.ActualizarUsuarioResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;

public class usuarioconfigViewModel extends ViewModel {
    private MutableLiveData<Class<? extends AppCompatActivity>> navigationEvent = new MutableLiveData<>();
    private Activity context;

    private MutableLiveData<ActualizarUsuarioResponse> actualizarUsuarioResponse = new MutableLiveData<>();

    public LiveData<Class<? extends AppCompatActivity>> getNavigationEvent() {
        return navigationEvent;
    }

    public void onArrowClicked() {
        navigationEvent.setValue(ConfigurationView.class);
    }

    public LiveData<ActualizarUsuarioResponse> getActualizarResponse() {
        return actualizarUsuarioResponse;
    }

    private UserRepository userRepository = new UserRepository();


    public void setContext(Activity context) {
        this.context = context;
    }

    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("CheckResult")
    public void updateUser(User user, String token) {


        if (user.getName() == null && user.getLastname() == null && user.getEmail() == null) {
            showMessage("No hay campos para actualizar");
            return;
        }

        userRepository.getActualizarResponse(user.getName(), user.getLastname(), user.getEmail(), token)
                .subscribe(response -> {
                    actualizarUsuarioResponse.setValue((ActualizarUsuarioResponse) response);

                    if (((ActualizarUsuarioResponse) response).getMessage() != null) {
                        context.startActivity(new Intent(context, ConfigurationView.class));
                        context.finish();
                    } else {
                        showMessage("Error al actualizar el usuario: " + ((ActualizarUsuarioResponse) response).getMessage());
                    }
                }, throwable -> {
                    actualizarUsuarioResponse.setValue(new ActualizarUsuarioResponse(throwable));
                    showMessage("Error al actualizar el usuario: " + throwable.getMessage());
                });

    }
}
