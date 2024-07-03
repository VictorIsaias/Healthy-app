package com.example.healthyapp.View.MainViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.healthyapp.Adapter.HabitsAdapter;
import com.example.healthyapp.Adapter.IngredienteAdapter;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.Habito;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.ViewModel.DietFirstViewModel;
import com.example.healthyapp.ViewModel.HabitDashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class HabitsDashboardView extends AppCompatActivity  implements View.OnClickListener{

    HabitDashboardViewModel viewModel;

    private final DevicesRepository repository =new DevicesRepository();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_dashboard);

        RecyclerView recyclerView = findViewById(R.id.habitosrecycler);

        findViewById(R.id.btnVentanaConfiguracion).setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(HabitDashboardViewModel.class);
        viewModel.actualizarLista(getTokenShared());

        repository.getDevicesResponse(getTokenShared(),getUserShared())
                .subscribe(response -> {
                    UserDevicesResponse res=(UserDevicesResponse) response;
                    saveUserShared(res.getData());

                }, throwable -> {

                });

        Activity contexto = this;


        viewModel.getHabitos().observe(this, new Observer<List<Habito>>() {
            @Override
            public void onChanged(List<Habito> lista) {
                if(lista.size()>0){
                    HabitsAdapter habitsAdapter = new HabitsAdapter(lista,contexto,getUserShared(),getTokenShared());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(habitsAdapter);

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ConfigurationView.class);
        this.startActivity(intent);
        this.finish();
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences =getSharedPreferences("my_app_prefs", MODE_PRIVATE);
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