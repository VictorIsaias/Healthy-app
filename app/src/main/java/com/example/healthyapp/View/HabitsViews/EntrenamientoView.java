package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.DeviceObjects.Sensor;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.DistanciaResponse;
import com.example.healthyapp.Response.PasosResponse;
import com.example.healthyapp.Response.RitmoResponse;
import com.example.healthyapp.ViewModel.EntrenamientoViewModel;

public class EntrenamientoView extends AppCompatActivity implements View.OnClickListener{

    private EntrenamientoViewModel entrenamientoViewModel;
    private TextView pasosTxt, distanciaTxt, bpmTxt;
    private Button botonIniciar;
    private boolean timerRunning = false;
    private Handler handler;
    private final int delay = 3000; // 3 segundos

    Boolean ritmo=false,pasos=false,distancia=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_view);

        pasosTxt = findViewById(R.id.pasos);
        bpmTxt = findViewById(R.id.bpm);
        distanciaTxt = findViewById(R.id.distancia);
        botonIniciar = findViewById(R.id.IniciarTraining);
        findViewById(R.id.info).setOnClickListener(this);
        findViewById(R.id.atras).setOnClickListener(this);
        String authToken = getTokenShared();

        entrenamientoViewModel = new ViewModelProvider(this).get(EntrenamientoViewModel.class);

        handler = new Handler();


        for (Dispositivo device: getUserShared().getDispositivo()) {
            if (device.getTipo_dispositivo().getId() == 1) {
                for (Sensor sensor : device.getSensores()) {
                    if (sensor.getSensorType().getId() == 1 && sensor.getActivo() == 1) {
                        ritmo = true;
                    } else if (sensor.getSensorType().getId() == 5 && sensor.getActivo() == 1) {
                        distancia = true;

                    } else if (sensor.getSensorType().getId() == 6 && sensor.getActivo() == 1) {
                        pasos = true;
                    }
                }

            }
        }
        if(!ritmo){
            bpmTxt.setText("N/A");
        }
        if(!distancia){
            distanciaTxt.setText("N/A");
        }
        if(!pasos){
            pasosTxt.setText("N/A");
        }

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerRunning) {
                    timerRunning = true;
                    botonIniciar.setText("Detener");
                    startTimer(authToken);
                }
                else {
                    timerRunning = false;
                    handler.removeCallbacksAndMessages(null);
                    botonIniciar.setText("Iniciar");
                }
            }
        });

        entrenamientoViewModel.getPasosResponse().observe(this, new Observer<PasosResponse>() {
            @Override
            public void onChanged(PasosResponse pasosResponse) {
                if(pasos){
                    if (pasosResponse != null) {
                        String pasosInfo = pasosResponse.getData().getRetained_message();
                        String pasosUnit = pasosResponse.getData().getUnit();
                        String pasos = pasosInfo + " " + pasosUnit;
                        pasosTxt.setText(pasos);
                    }
                    else {
                        Toast.makeText(EntrenamientoView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        entrenamientoViewModel.getRitmoResponse().observe(this, new Observer<RitmoResponse>() {
            @Override
            public void onChanged(RitmoResponse ritmoResponse) {
                if(ritmo){
                    if (ritmoResponse != null) {
                        String ritmoInfo = ritmoResponse.getData().getRetained_message();
                        String ritmoUnit = ritmoResponse.getData().getUnit();
                        String ritmo = ritmoInfo + " " + ritmoUnit;
                        bpmTxt.setText(ritmo);
                    }
                    else {
                        Toast.makeText(EntrenamientoView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        entrenamientoViewModel.getDistanciaResponse().observe(this, new Observer<DistanciaResponse>() {
            @Override
            public void onChanged(DistanciaResponse distanciaResponse) {
                if(distancia){
                    if (distanciaResponse != null) {
                        String distanciaInfo = distanciaResponse.getData().getRetained_message();
                        String distanciaUnit = distanciaResponse.getData().getUnit();
                        String distancia = distanciaInfo + " " + distanciaUnit;
                        distanciaTxt.setText(distancia);
                    }
                    else {
                        Toast.makeText(EntrenamientoView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void startTimer(String authToken) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    entrenamientoViewModel.actualizarPasos(authToken);
                    entrenamientoViewModel.actualizarRitmo(authToken);
                    entrenamientoViewModel.actualizarDistancia(authToken);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (timerRunning) {
                    startTimer(authToken);
                }
            }
        }, delay);
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){

            this.finish();

        }else{
            this.startActivity(new Intent(this, EntrenamientoIntroView.class));
        }
    }
    private User getUserShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }
}