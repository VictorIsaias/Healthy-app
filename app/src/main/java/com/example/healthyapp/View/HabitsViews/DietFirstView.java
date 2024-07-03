package com.example.healthyapp.View.HabitsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.healthyapp.Constants.AppConstants.CALORIAS_GLOBALES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthyapp.Adapter.IngredienteAdapter;
import com.example.healthyapp.Model.DeviceObjects.Configuration;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.DietFirstViewModel;

import java.io.Serializable;
import java.util.List;

public class DietFirstView extends AppCompatActivity implements View.OnClickListener {

    DietFirstViewModel viewModel;
    TextView calorias;
    List<Ingrediente> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_first);
        viewModel = new ViewModelProvider(this).get(DietFirstViewModel.class);

        findViewById(R.id.info).setOnClickListener(this);
        findViewById(R.id.nuevo_ingrediente).setOnClickListener(this);

         lista=(List<Ingrediente>) getIntent().getSerializableExtra("list");
        if(lista!=null){
            viewModel.anadirIngALista(lista);

        }

        findViewById(R.id.btnTerminarReceta).setOnClickListener(this);
        calorias = findViewById(R.id.caloriasEdit);
        findViewById(R.id.atras).setOnClickListener(this);

        User confs=getUserShared();
        for (Configuration conf: confs.getConfigurations()) {

            if (conf.getTipo_configuracion_id()==3) {

                calorias.setText(conf.getData());

            }
        }

        RecyclerView recyclerView=findViewById(R.id.alimentos);
        viewModel.getIngredientes().observe(this, new Observer<List<Ingrediente>>() {
            @Override
            public void onChanged(List<Ingrediente> lista) {
                if(lista.size()>0){

                    IngredienteAdapter adaptador = new IngredienteAdapter(lista);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adaptador);

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            this.finish();
        }else if(view.getId()==R.id.nuevo_ingrediente){

            viewModel.anadirIngALista(this);
        }
        else if (view.getId()==R.id.info)

        {
            this.startActivity(new Intent(this, DietIntroView.class));
        }
        else if (view.getId()==R.id.btnTerminarReceta) {
            Intent intent=new Intent(this,DietThirdView.class);

            intent.putExtra("lista",(Serializable) lista);
            this.startActivity(intent);
            this.finish();
        }
    }

    private User getUserShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }

}