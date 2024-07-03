package com.example.healthyapp.View.MainViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.View.UserViews.LoginView;
import com.example.healthyapp.ViewModel.ConfigurationViewModel;

public class ConfigurationView extends AppCompatActivity implements View.OnClickListener{

    private ConfigurationViewModel viewModel;
    Activity act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        viewModel = new ViewModelProvider(this).get(ConfigurationViewModel.class);
        findViewById(R.id.btnVentanaHabitos).setOnClickListener(this);
        TextView nombre = findViewById(R.id.nombreUser);
        observeNavigation();
        nombre.setText(getUserShared().getName());
        act=this;
        Button botonusuario = findViewById(R.id.buttonUserSettings);
        Button botoncontra = findViewById(R.id.buttonPassword);
        Button botonhabit = findViewById(R.id.buttonhabitos);
        Button botonlog = findViewById(R.id.buttonLogout);
        Button botondispo = findViewById(R.id.buttondispositivo);
        botonusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onUserSettingsClicked();
            }
        });

        botondispo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewModel.onDeviceSettingsClicked();
            }
        });

        botoncontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onChangePasswordClicked();
            }
        });

        botonhabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onHabitConfigClicked();
            }
        });

        botonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(ConfigurationView.this, LoginView.class));
                act.finish();
            }
        });
    }

    private void observeNavigation() {
        viewModel.getNavigationEvent().observe(this, new Observer<Class<? extends AppCompatActivity>>() {
            @Override
            public void onChanged(Class<? extends AppCompatActivity> destination) {
                if (destination != null) {
                    startActivity(new Intent(ConfigurationView.this, destination));
                    act.finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, HabitsDashboardView.class);
        this.startActivity(intent);
        this.finish();
    }
    private User getUserShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }
}
