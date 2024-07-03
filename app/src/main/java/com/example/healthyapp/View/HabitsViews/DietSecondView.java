package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.Adapter.AlimentoAdapter;
import com.example.healthyapp.Model.HabitsModels.DietSecondModel;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.DietSecondViewModel;

import java.io.Serializable;
import java.util.List;

public class DietSecondView extends AppCompatActivity implements View.OnClickListener{

    DietSecondViewModel viewModel;

    TextView peso;
    EditText texto;
    List<Ingrediente> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_second);


         lista=(List<Ingrediente>) getIntent().getSerializableExtra("lista");

        findViewById(R.id.atras).setOnClickListener(this);
        peso = findViewById(R.id.pesoPesa);
        RecyclerView recyclerView = findViewById(R.id.alimentos);
        viewModel = new ViewModelProvider(this).get(DietSecondViewModel.class);
        viewModel.getModelo().observe(this, new Observer<DietSecondModel>() {
            @Override
            public void onChanged(DietSecondModel modelo) {
                if(modelo.getMessage()==null){
                    peso.setText(modelo.getPeso());
                    if (modelo.getAlimentos().size() > 0) {

                        AlimentoAdapter adaptador = new AlimentoAdapter(modelo.getAlimentos(),lista,DietSecondView.this, modelo.getPeso());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adaptador);

                    }
                }
                else {
                    Toast.makeText(DietSecondView.this, modelo.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        findViewById(R.id.btnBuscar).setOnClickListener(this);
        texto = findViewById(R.id.buscar);

        CountDownTimer ti=new CountDownTimer(100000000,8000) {
            @Override
            public void onTick(long l) {
                viewModel.actualizarPeso(getTokenShared());

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            Intent intent = new Intent(this, DietFirstView.class);

            intent.putExtra("list",(Serializable) lista);
            this.startActivity(intent);

            this.finish();
        }else{
            viewModel.actualizarLista(texto.getText().toString(),getTokenShared());
        }
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
}
