package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healthyapp.Model.Objects.ScreenSelectorRequest;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.ScreenSelectorResponse;
import com.example.healthyapp.ViewModel.ScreenSelectorViewModel;

public class ScreenSelectorView extends AppCompatActivity implements View.OnClickListener{

    ImageButton tap1, tap2, tap3, tap4;
    ScreenSelectorViewModel screenSelectorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_selector_view);

        String authToken = getTokenShared();
        findViewById(R.id.atras).setOnClickListener(this);
        tap1 = findViewById(R.id.p1);
        tap2 = findViewById(R.id.p2);
        tap3 = findViewById(R.id.p3);
        tap4 = findViewById(R.id.p4);
        screenSelectorViewModel = new ViewModelProvider(this).get(ScreenSelectorViewModel.class);

        tap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenSelectorRequest request = new ScreenSelectorRequest("1");
                screenSelectorViewModel.selectScreen(authToken, request).observe(ScreenSelectorView.this, new Observer<ScreenSelectorResponse>() {
                    @Override
                    public void onChanged(ScreenSelectorResponse screenSelectorResponse) {
                        if (screenSelectorResponse != null) {
                            Toast.makeText(ScreenSelectorView.this, "Pasos y distancia seleccionados", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ScreenSelectorView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenSelectorRequest request = new ScreenSelectorRequest("2");
                screenSelectorViewModel.selectScreen(authToken, request).observe(ScreenSelectorView.this, new Observer<ScreenSelectorResponse>() {
                    @Override
                    public void onChanged(ScreenSelectorResponse screenSelectorResponse) {
                        if (screenSelectorResponse != null) {
                            Toast.makeText(ScreenSelectorView.this, "Ritmo cardiaco seleccionado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ScreenSelectorView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenSelectorRequest request = new ScreenSelectorRequest("3");
                screenSelectorViewModel.selectScreen(authToken, request).observe(ScreenSelectorView.this, new Observer<ScreenSelectorResponse>() {
                    @Override
                    public void onChanged(ScreenSelectorResponse screenSelectorResponse) {
                        if (screenSelectorResponse != null) {
                            Toast.makeText(ScreenSelectorView.this, "Temperatura seleccionada", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ScreenSelectorView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenSelectorRequest request = new ScreenSelectorRequest("4");
                screenSelectorViewModel.selectScreen(authToken, request).observe(ScreenSelectorView.this, new Observer<ScreenSelectorResponse>() {
                    @Override
                    public void onChanged(ScreenSelectorResponse screenSelectorResponse) {
                        if (screenSelectorResponse != null) {
                            Toast.makeText(ScreenSelectorView.this, "Reloj seleccionado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ScreenSelectorView.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}