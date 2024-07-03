package com.example.healthyapp.Retrofit;

import com.example.healthyapp.Model.DeviceObjects.Configuration;
import com.example.healthyapp.Model.Objects.Lista;
import com.example.healthyapp.Model.UserObjects.PassRecoveryRequest;
import com.example.healthyapp.Model.Objects.ScreenSelectorRequest;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.Model.UserObjects.UsuarioRegistro;
import com.example.healthyapp.Model.UserObjects.VerificacionUsuario;
import com.example.healthyapp.Response.ActualizarContraResponse;
import com.example.healthyapp.Response.ActualizarUsuarioResponse;
import com.example.healthyapp.Response.DeviceTypeResponse;
import com.example.healthyapp.Response.DietResponse;
import com.example.healthyapp.Response.DistanciaResponse;
import com.example.healthyapp.Response.FoodResponse;
import com.example.healthyapp.Response.HabitosconfigResponse;
import com.example.healthyapp.Response.HabitsResponse;
import com.example.healthyapp.Response.LoginResponse;
import com.example.healthyapp.Response.PassRecoveryResponse;
import com.example.healthyapp.Response.PasosResponse;
import com.example.healthyapp.Response.RegistroResponse;
import com.example.healthyapp.Response.DefaultResponse;
import com.example.healthyapp.Response.SensorResponse;
import com.example.healthyapp.Response.TemperaturaResponse;
import com.example.healthyapp.Response.UserDevicesResponse;
import com.example.healthyapp.Response.RitmoResponse;
import com.example.healthyapp.Response.ScreenSelectorResponse;
import com.example.healthyapp.Response.VerificacionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("users")
    Call<RegistroResponse> registrarUsuario(@Body UsuarioRegistro usuarioRegistro);
    @POST("users/auth-login")
    Call<VerificacionResponse> verifyUser(@Body VerificacionUsuario verificacionUsuario);
    @POST("users/login")
    @FormUrlEncoded
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("emqx/obtener-peso")
    Call<SensorResponse> getPeso(
            @Header("Authorization") String token);

    @GET("foods/obtener-alimento")
    Call<DefaultResponse<FoodResponse>> getFood(
            @Header("Authorization") String token,
            @Query("nombrealimento") String nombrealimento);

    @POST("emqx/obtener-alcohol")
    Call<SensorResponse> getAlcohol(
            @Header("Authorization") String token);


    @GET("habits")
    Call<HabitsResponse> getHabits(
            @Header("Authorization") String token
    );

    @POST("foods/calcular-nutricion")
    Call<DietResponse> getDiet(
            @Header("Authorization") String token,
            @Body Lista ingr
    );

    @POST("dispositivos/crear-dispositivo")
    @FormUrlEncoded
    Call<DefaultResponse> setNewDevice(
            @Header("Authorization") String token,
            @Field("tipoDispositivo") String tipoDispositivo,
            @Field("nombre") String nombre
    );

    @PUT("users/actualizar")
    Call<ActualizarUsuarioResponse> actualizarUsuario(@Body User usuario);

    @GET("users/{id}")
    Call<UserDevicesResponse> getUserDevices(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @DELETE("dispositivos/{id}")
    Call<DefaultResponse> eliminateDevice(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @GET("tipo-dispositivo")
    Call<DeviceTypeResponse> getDeviceType(
            @Header("Authorization") String token);

    @POST("configurations")
    @FormUrlEncoded
    Call<DefaultResponse> setNewConfigurations(
            @Header("Authorization") String token,
            @Field("tipo_configuracion_id") Integer tipoConfiguracionId,
            @Field("data") String data,
            @Field("user_id") Integer userId
    );

    @PUT("dispositivos/{id}")
    @FormUrlEncoded
    Call<DefaultResponse> updateDevice(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("name") String nombre
    );

    @PUT("sensor/{id}")
    Call<DefaultResponse> updateSensor(
            @Header("Authorization") String token,
            @Path("id") int id
    );
    @POST("users/recuperar-contra")
    Call<PassRecoveryResponse> recoveryPass(@Body PassRecoveryRequest passRecoveryRequest);

    @POST("emqx/obtener-pasos")
    Call<PasosResponse> getPasos(
            @Header("Authorization") String token);

    @POST("emqx/obtener-ritmo")
    Call<RitmoResponse> getRitmo(
            @Header("Authorization") String token);

    @POST("emqx/obtener-distancia")
    Call<DistanciaResponse> getDistancia(
            @Header("Authorization") String token);

    @POST("emqx/mandar-a-pantalla")
    Call<ScreenSelectorResponse> setPantalla(
            @Header("Authorization") String token,
            @Body ScreenSelectorRequest screenSelectorRequest
    );
    @PUT("users/actualizar")
    Call<ActualizarUsuarioResponse> actualizarUsuario(@Body User usuario, @Header("Authorization")String token);

    @PUT("users/update-password")
    Call<ActualizarContraResponse> actualizarContra(@Body User usuario, @Header("Authorization")String token);

    @PUT("configurations/{id}")
    Call<HabitosconfigResponse> configurarHabito(@Path("id") Integer id, @Body Configuration configuracion, @Header("Authorization") String token);

    @POST("emqx/obtener-temperatura")
    Call<SensorResponse> obtenerTemperatura(@Header("Authorization")String token);

    @POST("users/recuperar-contra")
    @FormUrlEncoded
    Call<DefaultResponse> sendMail(
            @Header("Authorization") String token,
            @Field("email") String email
    );

    @POST("users/RecuperarPassword")
    @FormUrlEncoded
    Call<DefaultResponse> recoverPassword(
            @Header("Authorization") String token,
            @Field("email") String email,
            @Field("verificationCode") String verificationCode,
            @Field("newPassword") String newPassword
    );
}
