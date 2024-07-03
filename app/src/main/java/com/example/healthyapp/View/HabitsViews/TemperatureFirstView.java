package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthyapp.R;

public class TemperatureFirstView extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_first);
        findViewById(R.id.botonmedir).setOnClickListener(this);
        findViewById(R.id.btnMedirTemperatura).setOnClickListener(this);
        findViewById(R.id.atras).setOnClickListener(this);
        findViewById(R.id.info).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            this.finish();
        } else if (view.getId()==R.id.info) {
            this.startActivity(new Intent(this, TemperatureIntroView.class));
        } else {
            this.startActivity(new Intent(this, TemperatureSecondView.class));
            this.finish();
        }
    }
}