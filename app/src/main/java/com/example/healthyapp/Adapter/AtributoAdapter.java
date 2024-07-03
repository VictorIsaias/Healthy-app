package com.example.healthyapp.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyapp.Model.FoodObjects.Atributo;
import com.example.healthyapp.R;

import java.util.List;

public class AtributoAdapter extends RecyclerView.Adapter<AtributoAdapter.ViewHolder> {


    private List<Atributo> milista;

    public AtributoAdapter(List<Atributo> milista) {
        this.milista = milista;
    }

    @NonNull
    @Override
    public AtributoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate( R.layout.diet_attribute,parent,false);


        return new AtributoAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AtributoAdapter.ViewHolder holder, int position) {

        Atributo atributo=milista.get(position);
        holder.setData(atributo);
    }

    @Override
    public int getItemCount() {
        return milista.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, valor, porcentaje;
        Atributo a;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            valor = itemView.findViewById(R.id.valor);
            porcentaje = itemView.findViewById(R.id.porcentaje);

        }

        @SuppressLint("SetTextI18n")
        public void setData(Atributo atributo) {
            a = atributo;
            nombre.setText(atributo.getLabel());
            valor.setText(atributo.getQuantity()+" "+atributo.getUnit());
            if(atributo.getPorcentaje()==null||atributo.getPorcentaje()==0){
                porcentaje.setText(" ");

            }else{
                porcentaje.setText(atributo.getPorcentaje()+" %");

            }
        }

    }
}
