package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.Adapter.AtributoAdapter;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.Estado;
import com.example.healthyapp.Model.FoodObjects.Receta;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.DietThirdViewModel;

import java.io.Serializable;
import java.util.List;


public class DietThirdView extends AppCompatActivity implements View.OnClickListener{
    DietThirdViewModel viewModel;
    TextView calorias, message,title,peso;
    ConstraintLayout estado;
    List<Ingrediente> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_third);

        calorias = findViewById(R.id.CaloriasTotales);

        findViewById(R.id.atras).setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(DietThirdViewModel.class);

        RecyclerView recyclerView=findViewById(R.id.atributos);
        peso=findViewById(R.id.PesoTotal);

        lista=(List<Ingrediente>) getIntent().getSerializableExtra("lista");
        viewModel.setAtributos(lista,getTokenShared());

        message = findViewById(R.id.mensaje);
        title = findViewById(R.id.title);
        estado=findViewById(R.id.estado);
        viewModel.getReceta().observe(this, new Observer<Receta>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Receta resultado) {
                if(resultado.getMessage()==null&&resultado.getAtributos()!=null){
                    AtributoAdapter adaptador = new AtributoAdapter(resultado.getAtributos());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adaptador);

                    calorias.setText("Calorias totales: "+resultado.getCalorias()+" cal");
                    viewModel.setMensaje(resultado.getCalorias());

                    peso.setText("Peso total: "+resultado.getPeso()+" gr");

                }
                else if(resultado.getMessage()!=null){
                    Toast.makeText(getApplicationContext(), resultado.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getEstado().observe(this, new Observer<Estado>() {
            @Override
            public void onChanged(Estado resultado) {
                title.setText(resultado.getTitulo());
                message.setText(resultado.getTexto());
                estado.setBackgroundColor(resultado.getColor());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DietFirstView.class);

        intent.putExtra("list",(Serializable) lista);
        this.startActivity(intent);

        this.finish();
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
}