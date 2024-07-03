package com.example.healthyapp.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthyapp.Model.DeviceObjects.Configuration;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Model.UserObjects.UsuarioRegistro;
import com.example.healthyapp.Model.UserObjects.VerificacionUsuario;
import com.example.healthyapp.Response.DefaultResponse;
import com.example.healthyapp.Response.LoginResponse;
import com.example.healthyapp.Response.RegistroResponse;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.Response.VerificacionResponse;
import com.example.healthyapp.Retrofit.ApiService;
import com.example.healthyapp.Retrofit.RetrofitBuilder;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.example.healthyapp.Response.ActualizarContraResponse;
import com.example.healthyapp.Response.ActualizarUsuarioResponse;
import com.example.healthyapp.Response.HabitosconfigResponse;
import com.example.healthyapp.Response.TemperaturaResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static UserRepository instance; //Instancia singleton del repository
    private ApiService apiService;
    public UserRepository() {
        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
    }
    // Método estático público para obtener la instancia singleton
    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    public LiveData<RegistroResponse> registerUser(UsuarioRegistro request) {
        MutableLiveData<RegistroResponse> data = new MutableLiveData<>();
        apiService.registrarUsuario(request).enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                else {
                    String errorMessage = "Error en el servidor";
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                            if (jsonObject.has("error")) {
                                errorMessage = jsonObject.get("error").getAsString();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    data.setValue(new RegistroResponse(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<VerificacionResponse> verifyUser(VerificacionUsuario verificacionUsuario) {
        MutableLiveData<VerificacionResponse> data = new MutableLiveData<>();
        apiService.verifyUser(verificacionUsuario).enqueue(new Callback<VerificacionResponse>() {
            @Override
            public void onResponse(Call<VerificacionResponse> call, Response<VerificacionResponse> response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    VerificacionResponse goodResponse = new VerificacionResponse(response.message(), statusCode);
                    data.setValue(goodResponse);
                } else {
                    String message;
                    if (response.errorBody() != null) {
                        message = response.errorBody().toString();
                    }
                    else {
                        message = "Error en la solicitud...";
                    }
                    VerificacionResponse errorResponse = new VerificacionResponse(message, statusCode);
                    data.setValue(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<VerificacionResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public @NonNull Single<Object> getLoginResponse(String email, String password) {
        return Single.create(emitter -> {
            apiService.login(email, password).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
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
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    emitter.onError(new Exception("Fallo en el servidor"));
                }
            });
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public @NonNull Single<Object> getActualizarResponse(String name, String lastname, String email){
            return Single.create(emitter -> {
                User usuario = new User(name, lastname, email);
                apiService.actualizarUsuario(usuario)
                        .enqueue(new Callback<ActualizarUsuarioResponse>() {
                            @Override
                            public void onResponse(Call<ActualizarUsuarioResponse> call, Response<ActualizarUsuarioResponse> response) {
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
                            public void onFailure(Call<ActualizarUsuarioResponse> call, Throwable t) {
                                emitter.onError(new Exception("Fallo en el servidor"));
                            }
                        });
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

    public @NonNull
    @io.reactivex.rxjava3.annotations.NonNull Single<Object> getActualizarResponse(String name, String lastname, String email, String token) {
        return Single.create(emitter -> {
            User user = new User(name, lastname, email);
            apiService.actualizarUsuario(user,"Bearer "+token)
                    .enqueue(new Callback<ActualizarUsuarioResponse>() {
                        @Override
                        public void onResponse(Call<ActualizarUsuarioResponse> call, Response<ActualizarUsuarioResponse> response) {
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
                        public void onFailure(Call<ActualizarUsuarioResponse> call, Throwable t) {
                            emitter.onError(new Exception("Fallo en el servidor"));
                        }
                    });
        }).subscribeOn(Schedulers.io());
    }

    public @NonNull Single<Object> getActualizarContra(String contrasenaActual, String nuevaContrasena,String token) {
        return Single.create(emitter -> {
            User user = new User(contrasenaActual, nuevaContrasena);
            apiService.actualizarContra(user,"Bearer "+token)
                    .enqueue(new Callback<ActualizarContraResponse>() {
                        @Override
                        public void onResponse(Call<ActualizarContraResponse> call, Response<ActualizarContraResponse> response) {
                            if (response.isSuccessful()) {
                                emitter.onSuccess(response.body());
                            } else {
                                String errorMessage = "Error en el servidor";
                                if (response.errorBody() != null) {
                                    try {
                                        String errorBodyString = response.errorBody().string();
                                        JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                        if (jsonObject.has("error")) {
                                            errorMessage = jsonObject.get("error").getAsString();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                emitter.onError(new Exception(errorMessage));
                            }
                        }

                        @Override
                        public void onFailure(Call<ActualizarContraResponse> call, Throwable t) {
                            emitter.onError(new Exception("Fallo en el servidor"));
                        }
                    });
        }).subscribeOn(Schedulers.io());
    }
    public @NonNull Single<Object> getConfiguracionHabito(Integer id, String data, String token) {
        return Single.create(emitter -> {
            Configuration configuracion = new Configuration(id, data);
            apiService.configurarHabito(id,configuracion,"Bearer "+token)
                    .enqueue(new Callback<HabitosconfigResponse>() {
                        @Override
                        public void onResponse(Call<HabitosconfigResponse> call, Response<HabitosconfigResponse> response) {
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
                        public void onFailure(Call<HabitosconfigResponse> call, Throwable t) {
                            emitter.onError(new Exception("Fallo en el servidor"));
                        }
                    });
        }).subscribeOn(Schedulers.io());
    }
    public @io.reactivex.rxjava3.annotations.NonNull Single<Object> sendMail(String token,String email) {
        return Single.create(emitter -> {
                    apiService.sendMail("Bearer "+token, email)
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.isSuccessful()) {
                                        emitter.onSuccess("Codigo enviado por email");
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


    public @io.reactivex.rxjava3.annotations.NonNull Single<Object> recoverPassword(String token,String email,String verificationCode,String newPassword) {
        return Single.create(emitter -> {
                    apiService.recoverPassword("Bearer "+token, email,verificationCode,newPassword)
                            .enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.isSuccessful()) {
                                        emitter.onSuccess("Contraseña actualizada correctamente");
                                    } else {
                                        String errorMessage = "error: Error en el servidor";
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
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    emitter.onError(new Exception("error: Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
