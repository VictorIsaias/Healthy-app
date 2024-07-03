package com.example.healthyapp.Model.HabitsModels;

import static com.example.healthyapp.Constants.AppConstants.CALORIAS_GLOBALES;

import android.graphics.Color;

import com.example.healthyapp.Model.Objects.Estado;

public class DietThirdModel {
    public static Estado CrearMensaje(Double cal){
        Estado estado;
        double dif = (CALORIAS_GLOBALES-cal);
        if(dif==0){
            estado=new Estado(Color.rgb(121, 192, 54),"Tus comidas de hoy estan cerca de la meta calorica",cal,"Estas justo en tu meta calorica");
        }
        else if(dif<=100&&dif>0){
            estado=new Estado(Color.rgb(121, 192, 54),"Tus comidas de hoy estan cerca de la meta calorica",cal,"Necesitas "+dif+" calorias para alcanzar tu meta calorica de "+CALORIAS_GLOBALES);
        }
        else if(dif>=-100&&dif<0){
            estado=new Estado(Color.rgb(121, 192, 54),"Tus comidas de hoy estan cerca de la meta calorica",cal,"Te sobran "+(dif*-1)+" calorias para estar en tu meta calorica de "+CALORIAS_GLOBALES);
        }
        else if(dif>100&&dif<=500){
            estado=new Estado(Color.rgb(209, 213, 60),"Tus comidas de hoy no alcanzan por poco tu meta calorica ",cal,"Necesitas "+dif+" calorias para alcanzar tu meta calorica de "+CALORIAS_GLOBALES);
        }
        else if(dif<-100&&dif>=-500){
            estado=new Estado(Color.rgb(209, 213, 60),"Tus comidas de hoy sobrepasan por poco tu meta calorica",cal,"Te sobran "+(dif*-1)+" calorias para estar en tu meta calorica de "+CALORIAS_GLOBALES);
        }
        else if(dif>500){
            estado=new Estado(Color.rgb(214, 75, 60),"Tus comidas de hoy no alcanzan por mucho tu meta calorica",cal,"Necesitas "+dif+" calorias para alcanzar tu meta calorica de "+CALORIAS_GLOBALES);
        }
        else if(dif<-500){
            estado=new Estado(Color.rgb(214, 75, 60),"Tus comidas de hoy sobrepasan por mucho tu meta calorica",cal,"Te sobran "+(dif*-1)+" calorias para estar en tu meta calorica de "+CALORIAS_GLOBALES);
        }
        else{
            estado=new Estado(Color.rgb(121, 192, 54),"Tus comidas de hoy estan cerca de la meta calorica",cal,"Estas justo en tu meta calorica");

        }

        return estado;
    }
}
