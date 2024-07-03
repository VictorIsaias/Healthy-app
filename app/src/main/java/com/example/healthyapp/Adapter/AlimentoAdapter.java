package com.example.healthyapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyapp.Model.FoodObjects.Atributo;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.FoodObjects.food;
import com.example.healthyapp.R;
import com.example.healthyapp.View.HabitsViews.DietFirstView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class AlimentoAdapter extends RecyclerView.Adapter<AlimentoAdapter.ViewHolder> {

    private List<food> milista;

    private List<Ingrediente> listaActual;

    String peso;
    private Activity contexto;
    public AlimentoAdapter(List<food> milista, List<Ingrediente> listaActual, Activity contexto,String peso){
        this.contexto=contexto;
        this.listaActual=listaActual;
        this.milista=milista;
        this.peso=peso;
    }
    @NonNull
    @Override
    public AlimentoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate( R.layout.diet_food,parent,false);


        return new AlimentoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentoAdapter.ViewHolder holder, int position) {

        food food =milista.get(position);
        holder.setData(food);
    }
    @Override
    public int getItemCount() {
        return milista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre;
        Button cuadro;
        food a;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.nombre);
            cuadro=itemView.findViewById(R.id.cuadro);
            image = itemView.findViewById(R.id.image);
            cuadro.setOnClickListener(this);

        }

        @SuppressLint("SetTextI18n")
        public void setData(food food) {
            a= food;
            nombre.setText(food.getLabel());
            if(a.getImage()!=null){
                Picasso.get()
                        .load(a.getImage())
                        .into(image);
            }else{
                image.setImageResource(R.drawable.baseline_question_mark_24);
            }

        }

        @Override
        public void onClick(View view) {
            peso = peso.replaceAll("[^0-9.]", "");

            double pes = Double.parseDouble(peso);
            if(pes<5){
                Toast.makeText(contexto, "El peso minimo de solicitud es de 5 gr.", Toast.LENGTH_SHORT).show();
            }else {
                food fd = milista.get(getLayoutPosition());
                fd.setPeso(new Atributo(pes));
                listaActual.add(new Ingrediente(fd,new Atributo("g",fd.getNutrients().getFAT()),new Atributo("kcal",fd.getNutrients().getENERC_KCAL()),new Atributo("g",fd.getNutrients().getPROCNT())));
                Intent intent = new Intent(contexto, DietFirstView.class);

                intent.putExtra("list",(Serializable) listaActual);
                contexto.startActivity(intent);

                contexto.finish();
            }


        }
    }
}
