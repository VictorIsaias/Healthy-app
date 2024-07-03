package com.example.healthyapp.View.MainViews;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.View.UserViews.LoginView;

import java.util.ArrayList;

public class splashView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Activity a=this;

        CountDownTimer ti=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {


            }

            @Override
            public void onFinish() {
                Intent intent;
                if (getUserShared()==null) {
                    intent=new Intent(a, LoginView.class);
                } else {
                    intent=new Intent(a, HabitsDashboardView.class);
                }
                a.startActivity(intent);
                a.finish();


            }
        }.start();

    }

    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
    private User getUserShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        String obj= sharedPreferences.getString("user", null);
        return ObjectSerializer.deserialize(obj, User.class);
    }
}