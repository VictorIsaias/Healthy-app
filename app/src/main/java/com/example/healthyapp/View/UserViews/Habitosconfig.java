package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthyapp.Adapter.HabitsAdapter;
import com.example.healthyapp.Model.DeviceObjects.Configuration;
import com.example.healthyapp.Model.Objects.Habito;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.HabitosconfigResponse;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;
import com.example.healthyapp.ViewModel.HabitosconfigViewModel;

import java.util.List;

public class Habitosconfig extends AppCompatActivity implements View.OnClickListener {
    private HabitosconfigViewModel viewModel;

    private final DevicesRepository repository =new DevicesRepository();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitosconfig);

        viewModel = new ViewModelProvider(this).get(HabitosconfigViewModel.class);

        observeNavigation();

        findViewById(R.id.atras).setOnClickListener(this);

        EditText editTextDistance = findViewById(R.id.editTextDistance);
        EditText editTextHeartRate = findViewById(R.id.editTextHeartRate);
        EditText editTextCalorieGoal = findViewById(R.id.editTextCalorieGoal);



        editTextCalorieGoal.setEnabled(false);
        editTextHeartRate.setEnabled(false);
        editTextDistance.setEnabled(false);
        User confs=getUserShared();

        for (Configuration conf: confs.getConfigurations()) {

            if (conf.getTipo_configuracion_id()==1) {

                editTextDistance.setEnabled(true);
                editTextDistance.setText(conf.getData());
            }

            if (conf.getTipo_configuracion_id()==2) {

                editTextHeartRate.setEnabled(true);
                editTextHeartRate.setText(conf.getData());
            }
            if (conf.getTipo_configuracion_id()==3) {
                editTextCalorieGoal.setEnabled(true);
                editTextCalorieGoal.setText(conf.getData());
            }
        }



        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dataSent = false; // Variable para verificar si se enviaron datos

                if(editTextDistance.getText()!=null) {
                    for (Configuration conf: getUserShared().getConfigurations()) {

                        if (conf.getTipo_configuracion_id() == 1) {
                            String distance = editTextDistance.getText().toString();
                            if (!distance.isEmpty()) {
                                 viewModel.configHabitos(conf.getId(), distance, getTokenShared());
                                dataSent = true; // Marcar que se enviaron datos
                            }
                        }
                    }

                }
                if(editTextHeartRate.getText()!=null) {
                    for (Configuration conf: getUserShared().getConfigurations()) {

                        if (conf.getTipo_configuracion_id()==2) {
                            String heartRate = editTextHeartRate.getText().toString();
                            if (!heartRate.isEmpty()) {
                                viewModel.configHabitos(conf.getId(), heartRate, getTokenShared());
                                dataSent = true; // Marcar que se enviaron datos
                            }
                        }
                    }

                }
                if(editTextCalorieGoal.getText()!=null) {
                    for (Configuration conf: getUserShared().getConfigurations()) {

                        if (conf.getTipo_configuracion_id()==3) {
                            String calorieGoal = editTextCalorieGoal.getText().toString();
                            if (!calorieGoal.isEmpty()) {
                                viewModel.configHabitos(conf.getId(), calorieGoal, getTokenShared());
                                dataSent = true; // Marcar que se enviaron datos
                            }
                        }
                    }

                }



                if (dataSent) {
                    Toast.makeText(Habitosconfig.this, "Configuración guardada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Habitosconfig.this, "Por favor ingrese datos para guardar la configuración", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void observeNavigation() {
        viewModel.getNavigationEvent().observe(this, new Observer<Class<? extends AppCompatActivity>>() {
            @Override
            public void onChanged(Class<? extends AppCompatActivity> destination) {
                if (destination != null) {
                    startActivity(new Intent(Habitosconfig.this, destination));
                }
            }
        });
    }
    private void saveTokenShared (String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }
    private User getUserShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }

    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }

    private void saveUserShared (User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedObject = ObjectSerializer.serialize(user);
        editor.putString("user", serializedObject);
        editor.apply();
    }
    @SuppressLint("CheckResult")
    @Override
    public void onClick(View view) {
        repository.getDevicesResponse(getTokenShared(),getUserShared())
                .subscribe(response -> {
                    UserDevicesResponse res=(UserDevicesResponse) response;
                    saveUserShared(res.getData());

                }, throwable -> {

                });

        this.startActivity(new Intent(this, ConfigurationView.class));
        this.finish();
    }
}
