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
import android.widget.Toast;

import com.example.healthyapp.Model.FoodObjects.Ingrediente;
import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.ForgotPassViewModel;

import java.util.List;

public class CodePasswordView extends AppCompatActivity implements View.OnClickListener {
    ForgotPassViewModel viewModel;
    ImageButton backbutton;
    EditText code,password;
    ImageButton eye;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_password);
        findViewById(R.id.atras).setOnClickListener(this);


        email=getIntent().getStringExtra("email");
        findViewById(R.id.btnVerificarCodigo).setOnClickListener(this);
        findViewById(R.id.reenviar).setOnClickListener(this);
        code=findViewById(R.id.codigo);
        eye=findViewById(R.id.eye);
        eye.setOnClickListener(this);
        password=findViewById(R.id.passwordNew);
        Activity a=this;

        viewModel = new ViewModelProvider(this).get(ForgotPassViewModel.class);
        viewModel.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(CodePasswordView.this, response, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getMensaje2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(CodePasswordView.this, response, Toast.LENGTH_SHORT).show();
                if (!response.startsWith("error")&&!response.startsWith("Usuario no verificado o datos incorrectos")) {
                    a.startActivity(new Intent(a, LoginView.class));
                    a.finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            Intent intent = new Intent(this,forgotPassView.class);
            this.startActivity(intent);
            this.finish();
        } else if (view.getId()==R.id.reenviar) {
            viewModel.sendMail(getTokenShared(),email);
        } else if (view.getId()==R.id.btnVerificarCodigo) {
            viewModel.verifycode(getTokenShared(),email,code.getText().toString(),password.getText().toString());
    } else if (view.getId()==R.id.eye) {
            if (password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eye.setImageResource(R.drawable.eye_closed);
            } else {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eye.setImageResource(R.drawable.eye);
            }
        }
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
}