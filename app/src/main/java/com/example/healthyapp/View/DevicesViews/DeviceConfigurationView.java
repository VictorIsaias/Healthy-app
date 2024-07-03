package com.example.healthyapp.View.DevicesViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Repository.HabitsRepository;
import com.example.healthyapp.ViewModel.DeviceConfigurationViewModel;
import com.example.healthyapp.ViewModel.NewDeviceViewModel;

import java.io.Serializable;
import java.util.List;

public class DeviceConfigurationView extends AppCompatActivity implements View.OnClickListener {

    EditText nombre;
    TextView tipoDispositivo;
    Dispositivo dispositivo;

    DeviceConfigurationViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_configuration);
        nombre=findViewById(R.id.nombreDispositivo);
        tipoDispositivo=findViewById(R.id.txtTipoDispositivo);
        viewModel = new ViewModelProvider(this).get(DeviceConfigurationViewModel.class);


        findViewById(R.id.btnVerSensores).setOnClickListener(this);
        findViewById(R.id.atras).setOnClickListener(this);
        findViewById(R.id.btnActualizarDispositivo).setOnClickListener(this);
        findViewById(R.id.btnEliminarDispositivo).setOnClickListener(this);

         dispositivo=(Dispositivo) getIntent().getSerializableExtra("dispositivo");
        nombre.setText(dispositivo.getNombre());
        tipoDispositivo.setText(dispositivo.getTipo_dispositivo().getName());

        Activity a=this;

        viewModel.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(a, response, Toast.LENGTH_SHORT).show();
                if (!response.startsWith("error")) {
                    viewModel.getUpdatedUser(getTokenShared(),getUserShared());
                    a.startActivity(new Intent(a, UserDevicesView.class));
                    a.finish();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User response) {
                if (response.getId()!=null) {

                    saveUserShared(response);
                }
            }
        });

    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.atras){
            this.startActivity(new Intent(this, UserDevicesView.class));
            this.finish();

        } else if (view.getId()== R.id.btnEliminarDispositivo) {
            viewModel.eliminateDevice(dispositivo.getId(),getTokenShared());
        } else if (view.getId()== R.id.btnActualizarDispositivo) {
            viewModel.updateDevice(dispositivo.getId(),nombre.getText().toString(),getTokenShared());
        }else if (view.getId()== R.id.btnVerSensores) {
            Intent intent = new Intent(this, DeviceSensorsView.class);
            intent.putExtra("dispositivo",(Serializable) dispositivo);

            this.startActivity(intent);
            this.finish();
        }
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
    private User getUserShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }
    private void saveUserShared (User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedObject = ObjectSerializer.serialize(user);
        editor.putString("user", serializedObject);
        editor.apply();
    }
}