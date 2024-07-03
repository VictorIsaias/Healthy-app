package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.Adapter.IngredienteAdapter;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.Objects.Tip;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.ViewModel.ControlAlcoholViewModel;
import com.example.healthyapp.ViewModel.DietFirstViewModel;

import java.util.List;

public class ControlAlcoholSecondView extends AppCompatActivity implements View.OnClickListener {

    ControlAlcoholViewModel viewModel;
    ProgressBar progressBar;
    TextView porcentaje,mensaje,txt1,txt2,txt3;
    ImageView img1,img2,img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_alcohol);
        findViewById(R.id.btnMedirAlcohol).setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(ControlAlcoholViewModel.class);
        viewModel.medirAlcohol(getTokenShared());
        progressBar=findViewById(R.id.progressAlcohol);
        findViewById(R.id.info).setOnClickListener(this);
        porcentaje=findViewById(R.id.porcentajeAlcohol);
        findViewById(R.id.atras).setOnClickListener(this);
        mensaje=findViewById(R.id.mensaje);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);


        viewModel.getAlcoholResponse().observe(this, new Observer<SensorResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(SensorResponse response) {
                if(response.getData().getRetained_message()!=null){
                    porcentaje.setText(response.getData().getRetained_message()+" "+response.getData().getUnit());

                    int p = (int) Math.round(( (Double.parseDouble(response.getData().getRetained_message())) / 450.0) * 100);

                    progressBar.setProgress(p);

                    mensaje.setText(response.getStatus());
                    txt1.setText(response.getTips().get(0).getMensaje());
                    txt2.setText(response.getTips().get(1).getMensaje());
                    txt3.setText(response.getTips().get(2).getMensaje());

                    img1.setImageResource(response.getTips().get(0).getImagen());
                    img2.setImageResource(response.getTips().get(1).getImagen());
                    img3.setImageResource(response.getTips().get(2).getImagen());

                }else{
                    Toast.makeText(ControlAlcoholSecondView.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnMedirAlcohol){
            viewModel.medirAlcohol(getTokenShared());

        }
         else if (view.getId()==R.id.info)

        {
            this.startActivity(new Intent(this, ControlAlcoholIntroView.class));
        }else if (view.getId()==R.id.atras) {
                this.finish();
         }
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }

}