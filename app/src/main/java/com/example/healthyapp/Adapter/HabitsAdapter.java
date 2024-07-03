package com.example.healthyapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.Objects.Habito;
import com.example.healthyapp.Model.DeviceObjects.Sensor;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.View.HabitsViews.EntrenamientoView;
import com.example.healthyapp.View.HabitsViews.ControlAlcoholFirstView;
import com.example.healthyapp.View.HabitsViews.DietFirstView;
import com.example.healthyapp.View.HabitsViews.ScreenSelectorView;
import com.example.healthyapp.View.HabitsViews.TemperatureFirstView;

import java.util.List;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.ViewHolder> {
    private final DevicesRepository repository =new DevicesRepository();

    List<Habito> habitoList;
    View view;
    Activity contexto;

    User user;
    String token;
    public HabitsAdapter(List<Habito> habitoList, Activity contexto,User user,String token) {
        this.habitoList = habitoList;
        this.contexto=contexto;
        this.token=token;
        this.user=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view1 = layoutInflater.inflate(R.layout.dashboard_rec, parent, false);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Habito habito = habitoList.get(position);
        holder.setdata(habito);
    }

    @Override
    public int getItemCount() {
        return habitoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titulo;
        TextView descripcion;
        ImageView image;
        Habito h;

        UserDevicesResponse response;
        List<Dispositivo> dispositivos;

        @SuppressLint("CheckResult")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            image = itemView.findViewById(R.id.img);
            itemView.findViewById(R.id.cuadro).setOnClickListener(this);
            view = itemView;


        }

        public void setdata(Habito habito) {
            h = habito;
            titulo.setText(habito.getName());
            descripcion.setText(habito.getDescription());
            switch (h.getId()) {
                case 1:
                    image.setImageResource(R.drawable.alcohol);
                    break;
                case 2:
                    image.setImageResource(R.drawable.corriendo);
                    break;
                case 3:
                    image.setImageResource(R.drawable.food);
                    break;
                case 4:
                    image.setImageResource(R.drawable.temperatura);
                    break;
                case 5:
                    image.setImageResource(R.drawable.smartwatch);
                    break;
            }
        }

        @SuppressLint("CheckResult")
        @Override
        public void onClick(View view) {
            repository.getDevicesResponse(token, user)
                    .subscribe(res -> {
                        response = (UserDevicesResponse) res;
                        dispositivos = response.getData().getDispositivo();

                        if(dispositivos.size()==0){
                            Toast.makeText(contexto, "No tienes ningun dispositivo registrado", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        switch (h.getId()){
                            case 1:
                                for (Dispositivo device: dispositivos) {
                                    if(device.getTipo_dispositivo().getId()==1){
                                        for (Sensor sensor: device.getSensores()) {
                                            if(sensor.getSensorType().getId()==4&&sensor.getActivo()==1){
                                                Intent intent=new Intent(contexto, ControlAlcoholFirstView.class);
                                                contexto.startActivity(intent);
                                                return;
                                            }
                                        }
                                        Toast.makeText(contexto, "Tu sensor de alcohol esta desactivado", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                Toast.makeText(contexto, "Necesitas registrar un brazalete Healthy", Toast.LENGTH_SHORT).show();
                                return;
                            case 2:
                                Boolean distancia=false,ritmo=false,pasos=false;
                                for (Dispositivo device: dispositivos) {
                                    if(device.getTipo_dispositivo().getId()==1){
                                        for (Sensor sensor: device.getSensores()) {
                                            if(sensor.getSensorType().getId()==1&&sensor.getActivo()==1){
                                                ritmo=true;
                                            } else if (sensor.getSensorType().getId()==5&&sensor.getActivo()==1) {
                                                distancia=true;

                                        } else if (sensor.getSensorType().getId()==6&&sensor.getActivo()==1) {
                                            pasos=true;
                                        }
                                        }
                                        if(ritmo&&distancia&&pasos){
                                            Intent intent=new Intent(contexto, EntrenamientoView.class);
                                            contexto.startActivity(intent);
                                            return;
                                        } else if (ritmo||distancia||pasos) {
                                            Toast.makeText(contexto, "Tu sensor de ritmo cardiaco, distancia o pasos estan desactivados", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(contexto, EntrenamientoView.class);
                                            contexto.startActivity(intent);
                                            return;
                                        }
                                        Toast.makeText(contexto, "Tus sensores de ritmo cardiaco, pasos y de distancia estan desactivados", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                Toast.makeText(contexto, "Necesitas registrar un brazalete Healthy", Toast.LENGTH_SHORT).show();
                                return;
                            case 3:
                                for (Dispositivo device: dispositivos) {
                                    if(device.getTipo_dispositivo().getId()==2){
                                        for (Sensor sensor: device.getSensores()) {
                                            if(sensor.getSensorType().getId()==2&&sensor.getActivo()==1){
                                                Intent intent=new Intent(contexto, DietFirstView.class);
                                                contexto.startActivity(intent);
                                                return;
                                            }
                                        }
                                        Toast.makeText(contexto, "Tu sensor de peso esta desactivado", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                Toast.makeText(contexto, "Necesitas registrar una pesa Healthy", Toast.LENGTH_SHORT).show();
                                return;
                            case 4:
                                for (Dispositivo device: dispositivos) {
                                    if(device.getTipo_dispositivo().getId()==1){
                                        for (Sensor sensor: device.getSensores()) {
                                            if(sensor.getSensorType().getId()==3&&sensor.getActivo()==1){
                                                Intent intent=new Intent(contexto, TemperatureFirstView.class);
                                                contexto.startActivity(intent);
                                                return;
                                            }
                                        }
                                        Toast.makeText(contexto, "Tu sensor de temperatura esta desactivado", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                Toast.makeText(contexto, "Necesitas registrar un brazalete Healthy", Toast.LENGTH_SHORT).show();
                                return;
                            case 5:
                                for (Dispositivo device: dispositivos) {
                                    if(device.getTipo_dispositivo().getId()==1){
                                        for (Sensor sensor: device.getSensores()) {
                                            if(sensor.getSensorType().getId()==7&&sensor.getActivo()==1){
                                                Intent intent=new Intent(contexto, ScreenSelectorView.class);
                                                contexto.startActivity(intent);
                                                return;
                                            }
                                        }
                                        Toast.makeText(contexto, "Tu pantalla esta desactivada", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                Toast.makeText(contexto, "Necesitas registrar un brazalete Healthy", Toast.LENGTH_SHORT).show();
                                return;
                        }

                    }, throwable -> {
                        Toast.makeText(contexto, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        }
    }


}
