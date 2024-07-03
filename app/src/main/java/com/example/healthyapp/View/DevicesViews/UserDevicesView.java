package com.example.healthyapp.View.DevicesViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.healthyapp.Adapter.DeviceAdapter;
import com.example.healthyapp.Adapter.HabitsAdapter;
import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;
import com.example.healthyapp.ViewModel.DietSecondViewModel;
import com.example.healthyapp.ViewModel.UserDevicesViewModel;

import java.util.List;

public class UserDevicesView extends AppCompatActivity implements View.OnClickListener {

    UserDevicesViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_devices);
        findViewById(R.id.btnAgregarDispositivo).setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(UserDevicesViewModel.class);
        viewModel.setDispositivos(getTokenShared(),getUserShared());
        RecyclerView recyclerView=findViewById(R.id.listaDispositivos);
        Activity contexto=this;

        findViewById(R.id.atras).setOnClickListener(this);
        viewModel.getDispositivos().observe(this, new Observer<UserDevicesResponse>() {
            @Override
            public void onChanged(UserDevicesResponse response) {
                DeviceAdapter adapter = new DeviceAdapter(response.getData().getDispositivo(),contexto);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            this.startActivity(new Intent(this, ConfigurationView.class));
            this.finish();
        }else if(view.getId()==R.id.btnAgregarDispositivo){
            this.startActivity(new Intent(this, NewDeviceView.class));

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
}