package com.example.healthyapp.Repository;

import com.example.healthyapp.Model.DeviceObjects.Tipo_dispositivo;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Response.DefaultResponse;
import com.example.healthyapp.Response.DeviceTypeResponse;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.Retrofit.ApiService;
import com.example.healthyapp.Retrofit.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevicesRepository {

    public DevicesRepository(){
        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

    }
    private ApiService apiService;

    public @NonNull Single<Object> getDevicesResponse(String token, User user) {
        return Single.create(emitter -> {
                    apiService.getUserDevices("Bearer "+token,user.getId())
                            .enqueue(new Callback<UserDevicesResponse>() {
                                @Override
                                public void onResponse(Call<UserDevicesResponse> call, Response<UserDevicesResponse> response) {
                                    if (response.isSuccessful()) {
                                        UserDevicesResponse res=response.body();




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
                                public void onFailure(Call<UserDevicesResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public @NonNull Single<Object> setNewDevice(String tipoDispositivo, String nombre, String token) {
        return Single.create(emitter -> {
                    apiService.setNewDevice("Bearer "+token, tipoDispositivo,  nombre)
                            .enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if (response.isSuccessful()) {




                                        emitter.onSuccess("Dispositivo creado");
                                    } else {
                                        String errorMessage = "error: Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = "error: "+jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception("error: "+errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    emitter.onError(new Exception("error: Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public @NonNull Single<Object> eliminateDevice(Integer id,String token) {
        return Single.create(emitter -> {
                    apiService.eliminateDevice("Bearer "+token, id)
                            .enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if (response.isSuccessful()) {
                                        emitter.onSuccess("Dispositivo eliminado");
                                    } else {
                                        String errorMessage = "error: Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = "error: "+jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception("error: "+errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    emitter.onError(new Exception("error: Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public @NonNull Single<Object> getDeviceTypes(String token) {
        return Single.create(emitter -> {
                    apiService.getDeviceType("Bearer "+token)
                            .enqueue(new Callback<DeviceTypeResponse>() {
                                @Override
                                public void onResponse(Call<DeviceTypeResponse> call, Response<DeviceTypeResponse> response) {
                                    if (response.isSuccessful()) {
                                        DeviceTypeResponse res=response.body();
                                        List<String> array=new ArrayList<>();
                                        for (Tipo_dispositivo tipo: res.getData()
                                             ) {
                                            array.add(tipo.getName());
                                        }
                                        emitter.onSuccess(array);
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
                                public void onFailure(Call<DeviceTypeResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void setNewConfigurations(String tipoDispositivoId,String token,User user) {
        List<String> mensajes=new ArrayList<>();
        switch (tipoDispositivoId) {
            case "pesa":
                setNewConfiguration( 3, "0", user.getId(),token)
                        .subscribe(response -> {

                            mensajes.add((String) response);
                        }, throwable -> {
                            mensajes.add(throwable.getMessage());
                        });
                break;
            case "brazalete":
                setNewConfiguration( 1, "0", user.getId(),token)
                        .subscribe(response -> {

                            mensajes.add((String) response);
                        }, throwable -> {
                            mensajes.add(throwable.getMessage());
                        });
                setNewConfiguration( 2, "0", user.getId(),token)
                        .subscribe(response -> {

                            mensajes.add((String) response);
                        }, throwable -> {
                            mensajes.add(throwable.getMessage());
                        });

                break;
        }

    }

    public @NonNull Single<Object> setNewConfiguration(Integer tipoConfiguracionId,String data,Integer userId,String token) {


        return Single.create(emitter -> {
                    apiService.setNewConfigurations("Bearer "+token, tipoConfiguracionId,  data,userId)
                            .enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if (response.isSuccessful()) {




                                        emitter.onSuccess("Dispositivo creado");
                                    } else {
                                        String errorMessage = "error: Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = "error: "+jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception("error: "+errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    emitter.onError(new Exception("error: Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public @NonNull Single<Object> updateDevice(Integer id,String nombre,String token) {
        return Single.create(emitter -> {
                    apiService.updateDevice("Bearer "+token, id,nombre)
                            .enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    if (response.isSuccessful()) {
                                        emitter.onSuccess("Dispositivo actualizado");
                                    } else {
                                        String errorMessage = "error: Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = "error: "+jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception("error: "+errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    emitter.onError(new Exception("error: Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public @NonNull Single<Object> updateSensor(int id,String token) {
        return Single.create(emitter -> {
                    apiService.updateSensor("Bearer "+token, id)
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.isSuccessful()) {
                                        DefaultResponse res = response.body();
                                        emitter.onSuccess(res.getMessage());
                                    } else {
                                        String errorMessage = "error: Error en el servidor";
                                        if (response.errorBody() != null) {
                                            try {
                                                String errorBodyString = response.errorBody().string();
                                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                                if (jsonObject.has("message")) {
                                                    errorMessage = "error: "+jsonObject.get("message").getAsString();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        emitter.onError(new Exception("error: "+errorMessage));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    emitter.onError(new Exception("error: Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}