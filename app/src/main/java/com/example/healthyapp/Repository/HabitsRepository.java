package com.example.healthyapp.Repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.example.healthyapp.Model.Objects.ScreenSelectorRequest;
import com.example.healthyapp.Response.DefaultResponse;
import com.example.healthyapp.Response.DistanciaResponse;
import com.example.healthyapp.Response.PasosResponse;
import com.example.healthyapp.Response.RitmoResponse;
import com.example.healthyapp.Response.ScreenSelectorResponse;

import com.example.healthyapp.Model.FoodObjects.Atributo;
import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.Model.FoodObjects.Receta;
import com.example.healthyapp.Model.FoodObjects.food;
import com.example.healthyapp.Model.FoodObjects.hintsList;
import com.example.healthyapp.Model.Objects.Lista;
import com.example.healthyapp.Model.Objects.Tip;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.DietResponse;
import com.example.healthyapp.Response.FoodResponse;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.Retrofit.ApiService;
import com.example.healthyapp.Retrofit.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitsRepository {
    public HabitsRepository(){
        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

    }
    private ApiService apiService;

    public @NonNull Single<Object> getFoodResponse(String nombrealimento,String token) {
        return Single.create(emitter -> {
                    apiService.getFood("Bearer " + token,nombrealimento)
                            .enqueue(new Callback<DefaultResponse<FoodResponse>>() {
                                @Override
                                public void onResponse(Call<DefaultResponse<FoodResponse>> call, Response<DefaultResponse<FoodResponse>> response) {
                                    if (response.isSuccessful()) {
                                        List<food> food=new ArrayList<>();
                                        DefaultResponse<FoodResponse> ja = (DefaultResponse<FoodResponse>) response.body();
                                        assert ja != null;
                                        for (hintsList hint:ja.getData().getHints()
                                        ) {
                                            food.add(hint.getFood());
                                        }
                                        assert response.body() != null;
                                        FoodResponse res = response.body().getData();
                                        res.setFoodList(food);
                                        emitter.onSuccess(res);
                                    } else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse<FoodResponse>> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public @NonNull Single<Object> getDietResponse(List<Ingrediente> alimentos,String token) {
        List<String> l=new ArrayList<>();
        for (Ingrediente alim: alimentos) {
            l.add(alim.getComida().getLabel()+" "+alim.getComida().getPeso().getQuantity());
        }
        Lista ingr = new Lista(l);

        return Single.create(emitter -> {
                    apiService.getDiet("Bearer " + token,ingr)
                            .enqueue(new Callback<DietResponse>() {
                                @Override
                                public void onResponse(Call<DietResponse> call, Response<DietResponse> response) {
                                    if (response.isSuccessful()) {
                                        DietResponse res = response.body();
                                        List<Atributo> ats=new ArrayList<>();

                                        DecimalFormat df = new DecimalFormat("#.##");

                                        ats.add(new Atributo("Energía", res.getTotalNutrients().getENERC_KCAL().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getENERC_KCAL().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getENERC_KCAL().getQuantity()))));

                                        ats.add(new Atributo("Grasa total", res.getTotalNutrients().getFAT().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFAT().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getFAT().getQuantity()))));

                                        if(res.getTotalNutrients().getFASAT()!=null){
                                            ats.add(new Atributo("Grasas saturadas", res.getTotalNutrients().getFASAT().getUnit(),
                                                    Double.parseDouble(df.format(res.getTotalNutrients().getFASAT().getQuantity()))));

                                        }

                                        ats.add(new Atributo("Grasas trans", res.getTotalNutrients().getFATRN().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFATRN().getQuantity()))));
                                        if(res.getTotalNutrients().getFAMS()!=null) {
                                            ats.add(new Atributo("Grasas monoinsaturadas", res.getTotalNutrients().getFAMS().getUnit(),
                                                    Double.parseDouble(df.format(res.getTotalNutrients().getFAMS().getQuantity()))));
                                        }
                                        ats.add(new Atributo("Grasas poliinsaturadas", res.getTotalNutrients().getFAPU().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFAPU().getQuantity()))));

                                        ats.add(new Atributo("Carbohidratos (diferencia)", res.getTotalNutrients().getCHOCDF().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getCHOCDF().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getCHOCDF().getQuantity()))));

                                        ats.add(new Atributo("Carbohidratos (netos)", res.getTotalNutrients().getCHOCDF().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getCHOCDF().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getCHOCDF().getQuantity()))));

                                        ats.add(new Atributo("Fibra dietética total", res.getTotalNutrients().getFIBTG().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFIBTG().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getFIBTG().getQuantity()))));

                                        ats.add(new Atributo("Azúcares totales (incluye NLEA)", res.getTotalNutrients().getSUGAR().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getSUGAR().getQuantity()))));

                                        ats.add(new Atributo("Proteína", res.getTotalNutrients().getPROCNT().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getPROCNT().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getPROCNT().getQuantity()))));

                                        ats.add(new Atributo("Colesterol", res.getTotalNutrients().getCHOLE().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getCHOLE().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getCHOLE().getQuantity()))));

                                        ats.add(new Atributo("Sodio", res.getTotalNutrients().getNA().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getNA().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getNA().getQuantity()))));

                                        ats.add(new Atributo("Calcio", res.getTotalNutrients().getCA().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getCA().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getCA().getQuantity()))));

                                        ats.add(new Atributo("Magnesio", res.getTotalNutrients().getMG().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getMG().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getMG().getQuantity()))));

                                        ats.add(new Atributo("Potasio", res.getTotalNutrients().getK().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getK().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getK().getQuantity()))));

                                        ats.add(new Atributo("Hierro", res.getTotalNutrients().getFE().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFE().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getFE().getQuantity()))));

                                        ats.add(new Atributo("Zinc", res.getTotalNutrients().getZN().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getZN().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getZN().getQuantity()))));

                                        ats.add(new Atributo("Fósforo", res.getTotalNutrients().getP().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getP().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getP().getQuantity()))));

                                        ats.add(new Atributo("Vitamina A (RAE)", res.getTotalNutrients().getVITA_RAE().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getVITA_RAE().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getVITA_RAE().getQuantity()))));

                                        ats.add(new Atributo("Vitamina C (ácido ascórbico)", res.getTotalNutrients().getVITC().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getVITC().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getVITC().getQuantity()))));

                                        ats.add(new Atributo("Tiamina (B1)", res.getTotalNutrients().getTHIA().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getTHIA().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getTHIA().getQuantity()))));

                                        ats.add(new Atributo("Riboflavina (B2)", res.getTotalNutrients().getRIBF().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getRIBF().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getRIBF().getQuantity()))));

                                        ats.add(new Atributo("Niacina (B3)", res.getTotalNutrients().getNIA().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getNIA().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getNIA().getQuantity()))));

                                        ats.add(new Atributo("Vitamina B6", res.getTotalNutrients().getVITB6A().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getVITB6A().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getVITB6A().getQuantity()))));
                                        ats.add(new Atributo("Folato (DFE)", res.getTotalNutrients().getFOLDFE().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFOLDFE().getQuantity()))));

                                        ats.add(new Atributo("Folato (alimenticio)", res.getTotalNutrients().getFOLFD().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFOLFD().getQuantity()))));

                                        ats.add(new Atributo("Ácido fólico", res.getTotalNutrients().getFOLAC().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getFOLAC().getQuantity()))));

                                        ats.add(new Atributo("Vitamina B12", res.getTotalNutrients().getVITB12().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getVITB12().getQuantity()))));

                                        ats.add(new Atributo("Vitamina D (D2 + D3)", res.getTotalNutrients().getVITD().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getVITD().getQuantity()))));

                                        ats.add(new Atributo("Vitamina E (alfa-tocoferol)", res.getTotalNutrients().getTOCPHA().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getTOCPHA().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getTOCPHA().getQuantity()))));

                                        ats.add(new Atributo("Vitamina K (filoquinona)", res.getTotalNutrients().getVITK1().getUnit(),
                                                Double.parseDouble(df.format(res.getTotalNutrients().getVITK1().getQuantity())),
                                                Double.parseDouble(df.format(res.getTotalDaily().getVITK1().getQuantity()))));

                                        Receta receta = new Receta(ats,new ArrayList<>(),res.getCalories(),res.getTotalWeight());
                                        emitter.onSuccess(receta);
                                    } else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DietResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public @NonNull Single<Object> getPesoResponse(String token) {
        return Single.create(emitter -> {
                    apiService.getPeso("Bearer " + token)
                            .enqueue(new Callback<SensorResponse>() {
                                @Override
                                public void onResponse(Call<SensorResponse> call, Response<SensorResponse> response) {
                                    if (response.isSuccessful()) {

                                        emitter.onSuccess(response.body());
                                    } else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<SensorResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public @NonNull Single<Object> getAlcoholResponse(String token) {
        return Single.create(emitter -> {
                    apiService.getAlcohol("Bearer " + token)
                            .enqueue(new Callback<SensorResponse>() {
                                @Override
                                public void onResponse(Call<SensorResponse> call, Response<SensorResponse> response) {
                                    if (response.isSuccessful()) {
                                        SensorResponse res = response.body();
                                        assert res != null;
                                        double data=Double.parseDouble(res.getData().getRetained_message());
                                        List<Tip> lista=new ArrayList<>();
                                        if(data==50.0){
                                            lista.add(new Tip(R.drawable.car,"Planifica con anticipación y evita beber si conduces"));
                                            lista.add(new Tip(R.drawable.remember,"Sé un buen amigo y ofrece alternativas seguras para volver a casa"));
                                            lista.add(new Tip(R.drawable.no_alcohol,"Conduce siempre sin consumir alcohol para garantizar la seguridad vial"));
                                            res.setStatus("Sobrio");

                                        } else if (data > 50.0 && data <=100.0) {
                                            lista.add(new Tip(R.drawable.taxi,"No subestimes los efectos del alcohol; busca alternativas seguras para conducir"));
                                            lista.add(new Tip(R.drawable.sick,"Observa cualquier señal de intoxicación y evita poner en riesgo tu seguridad"));
                                            lista.add(new Tip(R.drawable.martini,"Conoce tus límites y mantén el control sobre tu consumo de alcohol"));
                                            res.setStatus("Ligero");
                                        }
                                        else if (data > 100.0 && data <300.0) {
                                            lista.add(new Tip(R.drawable.car_accident,"Evita poner en riesgo tu seguridad y la de los demás; no conduzcas"));
                                            lista.add(new Tip(R.drawable.taxi,"Toma medidas preventivas y considera opciones de transporte alternativas"));
                                            lista.add(new Tip(R.drawable.no_alcohol,"Establece límites para ti mismo y evita situaciones donde puedas ser tentado a conducir bajo los efectos del alcohol"));
                                            res.setStatus("Ebrio");
                                        }
                                        else if (data >= 300.0) {
                                            lista.add(new Tip(R.drawable.taxi,"Nunca conduzcas si has consumido alcohol; busca una alternativa segura"));
                                            lista.add(new Tip(R.drawable.policia,"Conoce las graves consecuencias legales y personales de conducir bajo la influencia"));
                                            lista.add(new Tip(R.drawable.death,"Sé responsable y evita situaciones que puedan poner en peligro tu vida y la de los demás en la carretera"));
                                            res.setStatus("Intoxicado");
                                        }
                                        else{
                                            lista.add(new Tip(R.drawable.car,"Planifica con anticipación y evita beber si conduces"));
                                            lista.add(new Tip(R.drawable.remember,"Sé un buen amigo y ofrece alternativas seguras para volver a casa"));
                                            lista.add(new Tip(R.drawable.no_alcohol,"Conduce siempre sin consumir alcohol para garantizar la seguridad vial"));
                                            res.setStatus("Sobrio");
                                        }
                                        res.setTips(lista);
                                        emitter.onSuccess(res);
                                    } else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<SensorResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public  @NonNull Single<Object> getPasosResponse(String token) {
        return Single.create(emitter -> {
                    apiService.getPasos("Bearer " + token)
                            .enqueue(new Callback<PasosResponse>() {
                                @Override
                                public void onResponse(Call<PasosResponse> call, Response<PasosResponse> response) {
                                    if (response.isSuccessful()){
                                        emitter.onSuccess(response.body());
                                    }
                                    else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            }
                                            catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<PasosResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public  @NonNull Single<Object> getRitmoResponse(String token) {
        return Single.create(emitter -> {
                    apiService.getRitmo("Bearer " + token)
                            .enqueue(new Callback<RitmoResponse>() {
                                @Override
                                public void onResponse(Call<RitmoResponse> call, Response<RitmoResponse> response) {
                                    if (response.isSuccessful()){
                                        emitter.onSuccess(response.body());
                                    }
                                    else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            }
                                            catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<RitmoResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public  @NonNull Single<Object> getDistanciaResponse(String token) {
        return Single.create(emitter -> {
                    apiService.getDistancia("Bearer " + token)
                            .enqueue(new Callback<DistanciaResponse>() {
                                @Override
                                public void onResponse(Call<DistanciaResponse> call, Response<DistanciaResponse> response) {
                                    if (response.isSuccessful()){
                                        emitter.onSuccess(response.body());
                                    }
                                    else {
                                        String errorMessage = "Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = jsonObject.get("message").getAsString();
                                                }
                                            }
                                            catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception(errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DistanciaResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<ScreenSelectorResponse> selectScreen(String token, ScreenSelectorRequest request) {
        MutableLiveData<ScreenSelectorResponse> data = new MutableLiveData<>();
        apiService.setPantalla("Bearer " + token, request).enqueue(new Callback<ScreenSelectorResponse>() {
            @Override
            public void onResponse(Call<ScreenSelectorResponse> call, Response<ScreenSelectorResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ScreenSelectorResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public @androidx.annotation.NonNull Single<Object> getTemperaturaResponse(String token) {
        return Single.create(emitter -> {
            apiService.obtenerTemperatura("Bearer "+token)
                    .enqueue(new Callback<SensorResponse>() {
                        @Override
                        public void onResponse(Call<SensorResponse> call, Response<SensorResponse> response) {
                            if (response.isSuccessful()) {
                                SensorResponse res = response.body();
                                assert res != null;
                                double data=Double.parseDouble(res.getData().getRetained_message());
                                List<Tip> lista=new ArrayList<>();
                                if(data<36.5){
                                    lista.add(new Tip(R.drawable.jacket,"Mantente abrigado y busca refugio para elevar la temperatura corporal"));
                                    lista.add(new Tip(R.drawable.frio,"Evita la exposición prolongada al frío y utiliza capas de ropa adecuadas"));
                                    lista.add(new Tip(R.drawable.doctor,"Busca atención médica si experimentas síntomas de hipotermia, como escalofríos intensos o confusión"));
                                    res.setStatus("Baja temperatura corporal");

                                } else if (data > 36.5 && data <=37.5) {
                                    lista.add(new Tip(R.drawable.diet,"Mantén un estilo de vida saludable con una dieta equilibrada y ejercicio regular"));
                                    lista.add(new Tip(R.drawable.awa,"Hidrátate adecuadamente y busca mantener un equilibrio térmico cómodo"));
                                    lista.add(new Tip(R.drawable.temperature_list,"Monitorea tu temperatura regularmente y busca atención médica si experimentas cambios significativos sin causa aparente"));
                                    res.setStatus("Temperatura corporal normal ");
                                }
                                else if (data > 37.6 && data <=38) {
                                    lista.add(new Tip(R.drawable.zzz,"Descansa lo suficiente y evita el esfuerzo físico excesivo para permitir que tu cuerpo se recupere"));
                                    lista.add(new Tip(R.drawable.awa,"Toma líquidos en abundancia para mantener la hidratación y ayudar a reducir la fiebre"));
                                    lista.add(new Tip(R.drawable.medicine,"Considera el uso de medicamentos para reducir la fiebre según las indicaciones médicas si experimentas malestar significativo"));
                                    res.setStatus("Temperatura corporal alta");
                                }
                                else if (data >= 39) {
                                    lista.add(new Tip(R.drawable.sick,"Busca atención médica inmediata si experimentas fiebre alta, especialmente si va acompañada de otros síntomas graves"));
                                    lista.add(new Tip(R.drawable.awa,"Descansa y mantente hidratado para ayudar a tu cuerpo a combatir la infección"));
                                    lista.add(new Tip(R.drawable.doctor,"Sigue las recomendaciones de tu médico y no dudes en buscar ayuda profesional si la fiebre persiste o empeora"));
                                    res.setStatus("Fiebre");
                                }
                                res.setTips(lista);
                                emitter.onSuccess(res);
                            } else {
                                String errorMessage = "Error en el servidor";
                                if (response.errorBody() != null) {
                                    try {
                                        String errorBodyString = response.errorBody().string();
                                        JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                        if (jsonObject.has("message")) {
                                            errorMessage = jsonObject.get("message").getAsString();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                emitter.onError(new Exception(errorMessage));
                            }
                        }

                        @Override
                        public void onFailure(Call<SensorResponse> call, Throwable t) {
                            emitter.onError(new Exception("Fallo en el servidor"));
                        }
                    });
        }).subscribeOn(Schedulers.io());
    }
}



