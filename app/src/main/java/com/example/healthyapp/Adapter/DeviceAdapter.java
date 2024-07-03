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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.FoodObjects.Atributo;
import com.example.healthyapp.R;
import com.example.healthyapp.View.DevicesViews.DeviceConfigurationView;

import java.io.Serializable;
import java.util.List;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private Activity contexto;
    private List<Dispositivo> dispositivos;

    public DeviceAdapter(List<Dispositivo> dispositivos,Activity contexto) {
        this.contexto = contexto;
        this.dispositivos = dispositivos;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate( R.layout.diet_food,parent,false);


        return new DeviceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {

        Dispositivo dis=dispositivos.get(position);
        holder.setData(dis);
    }

    @Override
    public int getItemCount() {
        return dispositivos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Dispositivo d;

        TextView nombre;
        Button cuadro;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.nombre);
            cuadro=itemView.findViewById(R.id.cuadro);
            image = itemView.findViewById(R.id.image);
            cuadro.setOnClickListener(this);

        }

        @SuppressLint("SetTextI18n")
        public void setData(Dispositivo dispositivo) {
            d = dispositivo;
            nombre.setText(d.getNombre()+" - "+d.getTipo_dispositivo().getName());

            switch (d.getTipo_dispositivo_id()){
                case 1:
                    image.setImageResource(R.drawable.brazalete);
                    break;
                case 2:
                    image.setImageResource(R.drawable.pesa);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent =new Intent(contexto, DeviceConfigurationView.class);
            intent.putExtra("dispositivo",(Serializable) d);
            contexto.startActivity(intent);
            contexto.finish();
        }
    }
}
