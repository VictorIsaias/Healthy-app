package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.R;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.ViewModel.TemperaturaViewModel;

public class TemperatureSecondView extends AppCompatActivity implements View.OnClickListener {
    private TemperaturaViewModel viewModel;
    ProgressBar progressBar;
    TextView porcentaje,mensaje,txt1,txt2,txt3;
    ImageView img1,img2,img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura_second);
        findViewById(R.id.btnMedirTemperatura).setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(TemperaturaViewModel.class);
        viewModel.obtenerTemperatura(getTokenShared());
        progressBar=findViewById(R.id.progressTemperatura);
        porcentaje=findViewById(R.id.gradosTemperatura);
        findViewById(R.id.atras).setOnClickListener(this);
        findViewById(R.id.info).setOnClickListener(this);
        mensaje=findViewById(R.id.mensaje);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);


        viewModel.getTemperaturaValue().observe(this, new Observer<SensorResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(SensorResponse response) {
                if(response.getData().getRetained_message()!=null){
                    porcentaje.setText(response.getData().getRetained_message()+" "+response.getData().getUnit());

                    int p = (int) Math.round(( (Double.parseDouble(response.getData().getRetained_message())) / 40) * 100);

                    progressBar.setProgress(p);

                    mensaje.setText(response.getStatus());
                    txt1.setText(response.getTips().get(0).getMensaje());
                    txt2.setText(response.getTips().get(1).getMensaje());
                    txt3.setText(response.getTips().get(2).getMensaje());

                    img1.setImageResource(response.getTips().get(0).getImagen());
                    img2.setImageResource(response.getTips().get(1).getImagen());
                    img3.setImageResource(response.getTips().get(2).getImagen());

                }else{
                    Toast.makeText(TemperatureSecondView.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnMedirAlcohol){
            viewModel.obtenerTemperatura(getTokenShared());

        }
        else if (view.getId()==R.id.info)
        {
            this.startActivity(new Intent(this, TemperatureIntroView.class));
        }else if (view.getId()==R.id.atras) {
            this.finish();
        }
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }

}