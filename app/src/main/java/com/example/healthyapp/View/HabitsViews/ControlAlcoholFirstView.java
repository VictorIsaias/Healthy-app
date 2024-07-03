package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.healthyapp.R;

public class ControlAlcoholFirstView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_alcohol_first);
        findViewById(R.id.btnMedirAlcoholVerde).setOnClickListener(this);
        findViewById(R.id.btnMedirAlcohol).setOnClickListener(this);
        findViewById(R.id.atras).setOnClickListener(this);

        findViewById(R.id.info).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            this.finish();
        } else if (view.getId()==R.id.info) {
            this.startActivity(new Intent(this, ControlAlcoholIntroView.class));
        } else{
            this.startActivity(new Intent(this, ControlAlcoholSecondView.class));
            this.finish();

        }
    }
}