package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.ActualizarUsuarioResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;
import com.example.healthyapp.ViewModel.usuarioconfigViewModel;

public class usuarioconfig extends AppCompatActivity {

    private usuarioconfigViewModel viewModel;
    private EditText nombreEdit;
    private EditText apellidoEdit;
    private EditText emailEdit;
    Activity a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarioconfig);

        viewModel = new ViewModelProvider(this).get(usuarioconfigViewModel.class);
        a = this;
        viewModel.setContext(a);




        nombreEdit = findViewById(R.id.nombreedit);
        apellidoEdit = findViewById(R.id.apellidoedit);
        emailEdit = findViewById(R.id.editText);

        nombreEdit.setText(getUserShared().getName());
        apellidoEdit.setText(getUserShared().getLastname());
        emailEdit.setText(getUserShared().getEmail());
        ImageView botonflecha = findViewById(R.id.atras);

        botonflecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onArrowClicked();
            }
        });

        Button actualizarButton = findViewById(R.id.button3);
        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User us = new User();
                if(nombreEdit.getText()!=null){
                    us.setName(nombreEdit.getText().toString());
                } else if (apellidoEdit.getText()!=null) {
                    us.setLastname(apellidoEdit.getText().toString());

                } else if (emailEdit.getText()!=null) {
                    us.setEmail(emailEdit.getText().toString());
                }

                viewModel.updateUser(us,getTokenShared());
            }
        });
        viewModel.getNavigationEvent().observe(this, new Observer<Class<? extends AppCompatActivity>>() {
            @Override
            public void onChanged(Class<? extends AppCompatActivity> destination) {
                if (destination != null) {
                    a.startActivity(new Intent(usuarioconfig.this, destination));
                    a.finish();
                }
            }
        });
        viewModel.getActualizarResponse().observe(this, new Observer<ActualizarUsuarioResponse>() {
            @Override
            public void onChanged(ActualizarUsuarioResponse response) {
                if (response.getMessage() != null) {
                    Toast.makeText(usuarioconfig.this, "Se ha actualizado tu perfil con éxito", Toast.LENGTH_SHORT).show();
                    saveUserShared(response.getData());
                    Intent intent = new Intent(usuarioconfig.this, ConfigurationView.class);
                    a.startActivity(intent);
                    a.finish();
                } else {
                    Toast.makeText(usuarioconfig.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserShared (User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedObject = ObjectSerializer.serialize(user);
        editor.putString("user", serializedObject);
        editor.apply();
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
