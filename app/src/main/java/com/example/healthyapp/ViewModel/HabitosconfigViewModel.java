package com.example.healthyapp.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthyapp.Repository.UserRepository;
import com.example.healthyapp.Response.ActualizarContraResponse;
import com.example.healthyapp.Response.HabitosconfigResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;

public class HabitosconfigViewModel extends ViewModel {
    private MutableLiveData<Class<? extends AppCompatActivity>> navigationEvent = new MutableLiveData<>();
    public LiveData<Class<? extends AppCompatActivity>> getNavigationEvent() {
        return navigationEvent;
    }

    public void onArrowClicked(){
        navigationEvent.setValue(ConfigurationView.class);
    }
    private UserRepository userRepository;
    private Context context;
    private MutableLiveData<HabitosconfigResponse> habitosconfigResponse = new MutableLiveData<>();
    public LiveData<HabitosconfigResponse> getConfiguracionHabito() {
        return habitosconfigResponse;
    }
    public HabitosconfigViewModel() {
        userRepository = new UserRepository();
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("CheckResult")
    public void configHabitos(Integer id, String data, String token) {
        if (id==null || data.isEmpty()) {
            showMessage("Por favor ingrese los datos");
            return;
        }
        userRepository.getConfiguracionHabito(id, data,token)
                .subscribe(response -> {
                    habitosconfigResponse.setValue((HabitosconfigResponse) response);
                }, throwable -> {
                    habitosconfigResponse.setValue(new HabitosconfigResponse(throwable));
                });
    }


}
