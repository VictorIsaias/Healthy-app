package com.example.healthyapp.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.example.healthyapp.Model.Objects.Habito;
import com.example.healthyapp.Response.HabitsResponse;
import com.example.healthyapp.Retrofit.ApiService;
import com.example.healthyapp.Retrofit.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public MainRepository() {
        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

    }
    private ApiService apiService;

    public @NonNull Single<Object> getHabitsResponse(String token) {
        return Single.create(emitter -> {
                    apiService.getHabits("Bearer "+token)
                            .enqueue(new Callback<HabitsResponse>() {
                                @Override
                                public void onResponse(Call<HabitsResponse> call, Response<HabitsResponse> response) {
                                    if (response.isSuccessful()) {
                                        HabitsResponse res = response.body();
                                        assert res != null;
                                        List<Habito> lista =res.getData();

                                        emitter.onSuccess(lista);
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
                                public void onFailure(Call<HabitsResponse> call, Throwable t) {
                                    emitter.onError(new Exception("Fallo en el servidor"));
                                }
                            });
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}