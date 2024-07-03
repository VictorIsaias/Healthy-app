package com.example.healthyapp.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthyapp.Model.DeviceObjects.Dispositivo;
import com.example.healthyapp.Model.DeviceObjects.Sensor;
import com.example.healthyapp.Model.FoodObjects.Atributo;
import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Repository.DevicesRepository;
import com.example.healthyapp.View.DevicesViews.DeviceConfigurationView;
import com.example.healthyapp.View.DevicesViews.DeviceSensorsView;

import java.io.Serializable;
import java.util.List;

public class SensorAdapter  extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    private DeviceSensorsView contexto;
    private List<Sensor> sensores;
    private final DevicesRepository repository =new DevicesRepository();

    public SensorAdapter(List<Sensor> sensores ,DeviceSensorsView contexto) {
        this.contexto = contexto;
        this.sensores = sensores;
    }

    @NonNull
    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate( R.layout.sensor_item,parent,false);
        return new SensorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, int position) {

        Sensor sen=sensores.get(position);
        holder.setData(sen);
    }

    @Override
    public int getItemCount() {
        return sensores.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Sensor s;

        TextView tipoSensores;
        CheckBox btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoSensores=itemView.findViewById(R.id.tipoSensor);
            btn=itemView.findViewById(R.id.activo);
            btn.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Sensor sensor) {
            s = sensor;
            tipoSensores.setText(sensor.getSensorType().getName());
            if(s.getActivo()==1){
                btn.setChecked(true);
            } else if (s.getActivo()==0) {
                btn.setChecked(false);
            }

        }

        @SuppressLint("CheckResult")
        @Override
        public void onClick(View view) {

            repository.updateSensor(s.getId(),getTokenShared())
                    .subscribe(response -> {
                        Toast.makeText(contexto, (String) response, Toast.LENGTH_SHORT).show();
                        Integer p=0;
                        for (int i=0;i<sensores.size();i++){
                            if(s==sensores.get(i)){
                                p=i;
                            }
                        }
                        if( sensores.get(p).getActivo()==1){
                            sensores.get(p).setActivo(0);

                        }else if( sensores.get(p).getActivo()==0){

                            sensores.get(p).setActivo(1);
                        }

                        contexto.actualizarDatos(sensores);
                    }, throwable -> {
                        Toast.makeText(contexto, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
    private User getUserShared() {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }
}
