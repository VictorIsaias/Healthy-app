package com.example.healthyapp.ViewModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.healthyapp.Repository.UserRepository;
import com.example.healthyapp.Response.ActualizarContraResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;



public class ActuContraViewModel extends ViewModel {
    private MutableLiveData<Class<? extends AppCompatActivity>> navigationEvent = new MutableLiveData<>();

    public LiveData<Class<? extends AppCompatActivity>> getNavigationEvent() {
        return navigationEvent;
    }

    public void onArrowClicked(){
        navigationEvent.setValue(ConfigurationView.class);
    }

    private UserRepository userRepository;
    @SuppressLint("StaticFieldLeak")
    private Activity context;

    private MutableLiveData<ActualizarContraResponse> actualizarcontraResponse = new MutableLiveData<>();
    public LiveData<ActualizarContraResponse> getActualizarContraResponse() {
        return actualizarcontraResponse;
    }
    public ActuContraViewModel() {
        userRepository = new UserRepository();
    }
    public void setContext(Activity context) {
        this.context = context;
    }
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("CheckResult")
    public void updateContra(String oldPassword, String newPassword,String token) {
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            showMessage("Por favor ingrese la contraseña actual y la nueva contraseña.");
            return;
        }

        userRepository.getActualizarContra(oldPassword, newPassword,token)
                .subscribe(response -> {
                    actualizarcontraResponse.setValue((ActualizarContraResponse) response);
                }, throwable -> {
                    showMessage(throwable.getMessage());
                });
    }

}
