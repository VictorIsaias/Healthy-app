package com.example.healthyapp.View.DevicesViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthyapp.Adapter.DeviceAdapter;
import com.example.healthyapp.Model.DeviceObjects.Tipo_dispositivo;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.Objects.Tip;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.View.MainViews.HabitsDashboardView;
import com.example.healthyapp.ViewModel.NewDeviceViewModel;
import com.example.healthyapp.ViewModel.UserDevicesViewModel;

import java.util.List;
import java.util.Objects;

public class NewDeviceView extends AppCompatActivity implements View.OnClickListener {

    EditText nombre;

    Spinner TipoDispositivo;
    NewDeviceViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);

        String p=getIntent().getStringExtra("if");

        Activity a=this;
        nombre=findViewById(R.id.nombreDispositivo);
        TipoDispositivo=findViewById(R.id.spinnerTipoDispositivo);
        findViewById(R.id.btnAgregarDispositivo).setOnClickListener(this);
        findViewById(R.id.atras).setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(NewDeviceViewModel.class);
        viewModel.setTipos_dispositivo(getTokenShared());
        viewModel.getTiposDispositivo().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> response) {
                ArrayAdapter<String> valores = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,response);
                valores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                TipoDispositivo.setAdapter(valores);
            }
        });
        viewModel.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(NewDeviceView.this, response, Toast.LENGTH_SHORT).show();
                if (!response.startsWith("error")) {

                    if(Objects.equals(p, "true")){
                        a.startActivity(new Intent(a, HabitsDashboardView.class));
                        a.finish();

                    }
                    else{
                        a.startActivity(new Intent(a, UserDevicesView.class));
                        a.finish();
                    }

                    viewModel.getUpdatedUser(getTokenShared(),getUserShared());

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

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnAgregarDispositivo){
            viewModel.setNewDevice(TipoDispositivo.getSelectedItem().toString(),nombre.getText().toString(),getTokenShared(),getUserShared());

        } else if (view.getId()==R.id.atras) {
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