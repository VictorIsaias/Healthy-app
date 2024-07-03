package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.ActualizarContraResponse;
import com.example.healthyapp.View.MainViews.ConfigurationView;
import com.example.healthyapp.ViewModel.ActuContraViewModel;

import java.util.Objects;

public class ActuContra extends AppCompatActivity implements View.OnClickListener {
    private ActuContraViewModel viewModel;
    ImageButton eye,eye2;
    EditText contrasenaActualEdit,nuevaContrasenaEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actu_contra);
        Activity a = this;

        viewModel = new ViewModelProvider(this).get(ActuContraViewModel.class);
        viewModel.setContext(this);
        eye=findViewById(R.id.eye);
        eye2=findViewById(R.id.eye2);
        eye.setOnClickListener(this);
        eye2.setOnClickListener(this);
        Button actualizarButton = findViewById(R.id.button3);
         contrasenaActualEdit = findViewById(R.id.contraedit);
         nuevaContrasenaEdit = findViewById(R.id.contranewedit);
        findViewById(R.id.atras).setOnClickListener(this);

        viewModel.getActualizarContraResponse().observe(this, new Observer<ActualizarContraResponse>() {
            @Override
            public void onChanged(ActualizarContraResponse response) {
                if (Objects.equals(response.getMessage(), "Contraseña de usuario actualizada")) {
                    Toast.makeText(ActuContra.this, "Se ha actualizado tu contrasena con éxito", Toast.LENGTH_SHORT).show();
                    saveUserShared(response.getData());
                    Intent intent = new Intent(ActuContra.this, ConfigurationView.class);
                    a.startActivity(intent);
                    a.finish();
                } else {
                    Toast.makeText(ActuContra.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getNavigationEvent().observe(this, new Observer<Class<? extends AppCompatActivity>>() {
            @Override
            public void onChanged(Class<? extends AppCompatActivity> destination) {
                if (destination != null) {
                    a.startActivity(new Intent(ActuContra.this, destination));
                }
            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contrasenaActual = contrasenaActualEdit.getText().toString();
                String nuevaContrasena = nuevaContrasenaEdit.getText().toString();

                viewModel.updateContra(contrasenaActual, nuevaContrasena,getTokenShared());
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){

        this.startActivity(new Intent(this, ConfigurationView.class));
        this.finish();

    } else if (view.getId()==R.id.eye) {
        if (contrasenaActualEdit.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            contrasenaActualEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eye.setImageResource(R.drawable.eye_closed);
        } else {
            contrasenaActualEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eye.setImageResource(R.drawable.eye);
        }
    }
     else if (view.getId()==R.id.eye2) {
        if (nuevaContrasenaEdit.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            nuevaContrasenaEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eye2.setImageResource(R.drawable.eye_closed);
        } else {
            nuevaContrasenaEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eye2.setImageResource(R.drawable.eye);
        }
    }
    }
}

