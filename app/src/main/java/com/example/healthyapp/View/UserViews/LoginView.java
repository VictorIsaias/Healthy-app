package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.Model.Objects.ObjectSerializer;
import com.example.healthyapp.Model.UserObjects.User;
import com.example.healthyapp.R;
import com.example.healthyapp.Response.LoginResponse;
import com.example.healthyapp.View.DevicesViews.NewDeviceView;
import com.example.healthyapp.View.MainViews.HabitsDashboardView;
import com.example.healthyapp.ViewModel.LoginViewModel;

public class LoginView extends AppCompatActivity implements View.OnClickListener{

    private LoginViewModel loginViewModel;
    private EditText emailTxt, passwordTxt;
    ImageButton eye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        Activity a=this;
        emailTxt = findViewById(R.id.correoLog);
        passwordTxt = findViewById(R.id.passwordLog);
        eye=findViewById(R.id.eye);
        eye.setOnClickListener(this);
        Button botonHecho = findViewById(R.id.hecho);
        TextView goToRegister = findViewById(R.id.goToRegister);
        TextView forgotPassword = findViewById(R.id.forgot);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        botonHecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();
                try {
                    loginViewModel.login(email, password);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        loginViewModel.getLoginResponse().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getData() !=null) {

                    saveTokenShared(loginResponse.getData().getToken().getToken());
                    saveUserShared(loginResponse.getData().getUser());
                    if(loginResponse.getData().getUser().getDispositivo().size()==0){
                        Toast.makeText(LoginView.this, "Registra dispositivos para empezar", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginView.this, NewDeviceView.class);
                        intent.putExtra("if","true");
                        a.startActivity(intent);
                        a.finish();

                    }else{
                        Toast.makeText(LoginView.this, "Inicio de sesion como "
                                +loginResponse
                                .getData()
                                .getUser()
                                .getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginView.this, HabitsDashboardView.class);
                        a.startActivity(intent);
                        a.finish();
                    }

                }
                else {
                    Toast.makeText(LoginView.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(loginResponse.getMessage().startsWith("El usuario aún no está verificado")){
                        Intent intent = new Intent(LoginView.this, AutenticarUsuario.class);
                        a.startActivity(intent);
                    }
                }
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regintent = new Intent(LoginView.this, RegistrerView.class);
                startActivity(regintent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotintent = new Intent(LoginView.this, forgotPassView.class);
                startActivity(forgotintent);
            }
        });
    }

    private void saveTokenShared (String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }
    private void saveUserShared (User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String serializedObject = ObjectSerializer.serialize(user);
        editor.putString("user", serializedObject);
        editor.apply();
    }


    @Override
    public void onClick(View view) {
            if (passwordTxt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                passwordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eye.setImageResource(R.drawable.eye_closed);
            } else {
                passwordTxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eye.setImageResource(R.drawable.eye);
            }

    }
}