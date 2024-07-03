package com.example.healthyapp.View.DevicesViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.healthyapp.Adapter.DeviceAdapter;
import com.example.healthyapp.Adapter.SensorAdapter;
import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.DeviceObjects.Sensor;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.UserDevicesResponse;

import java.io.Serializable;
import java.util.List;

public class DeviceSensorsView extends AppCompatActivity implements View.OnClickListener{

    List<Sensor> sensores;
    private final DevicesRepository repository =new DevicesRepository();
    Dispositivo dispositivo;
    SensorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_sensors);

        RecyclerView recyclerView = findViewById(R.id.listaSensores);
        findViewById(R.id.atras).setOnClickListener(this);
        findViewById(R.id.btnListo).setOnClickListener(this);

        dispositivo=(Dispositivo) getIntent().getSerializableExtra("dispositivo");
        sensores=dispositivo.getSensores();

         adapter = new SensorAdapter(sensores,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


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
        dispositivo.setSensores(sensores);
        Intent intent = new Intent(this, DeviceConfigurationView.class);
        intent.putExtra("dispositivo",(Serializable) dispositivo);

        this.startActivity(intent);
        this.finish();

    }

    public void actualizarDatos(List<Sensor> lista){
        sensores = lista;
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