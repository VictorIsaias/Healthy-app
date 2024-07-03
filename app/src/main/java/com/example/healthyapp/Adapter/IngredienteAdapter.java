package com.example.healthyapp.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredienteAdapter extends RecyclerView.Adapter<IngredienteAdapter.ViewHolder> {

    private List<Ingrediente> milista;
    public IngredienteAdapter(List<Ingrediente> milista){
        this.milista=milista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate( R.layout.diet_food_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingrediente ingrediente=milista.get(position);
        holder.setData(ingrediente);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void eliminar(View view, int pos) {
        milista.remove(pos);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return milista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre,proteinas,sodio,calorias,peso;
        ImageButton btn;
        Ingrediente i;
        View cuadro;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.nombre);
            proteinas=itemView.findViewById(R.id.proteina);
            calorias=itemView.findViewById(R.id.calorias);
            sodio=itemView.findViewById(R.id.sodio);
            peso=itemView.findViewById(R.id.peso);
            btn=itemView.findViewById(R.id.eliminar);
            btn.setOnClickListener(this);
            cuadro=itemView;
            imagen=itemView.findViewById(R.id.imagen);

        }

        @SuppressLint("SetTextI18n")
        public void setData(Ingrediente ingrediente) {
            i=ingrediente;
            peso.setText(ingrediente.getComida().getPeso().getQuantity()+" "+ingrediente.getComida().getPeso().getUnit());
            sodio.setText(ingrediente.getSodio().getQuantity()+" "+ingrediente.getSodio().getUnit());
            calorias.setText(ingrediente.getCalorias().getQuantity()+" "+ingrediente.getCalorias().getUnit());
            proteinas.setText(ingrediente.getProteinas().getQuantity()+" "+ingrediente.getProteinas().getUnit());
            nombre.setText(ingrediente.getComida().getLabel());
            if(i.getComida().getImage()!=null){
                Picasso.get()
                        .load(i.getComida().getImage())
                        .into(imagen);
            }else{
                imagen.setImageResource(R.drawable.baseline_question_mark_24);
            }
        }

        @Override
        public void onClick(View view) {
            eliminar(view,getLayoutPosition());
        }
    }
}
