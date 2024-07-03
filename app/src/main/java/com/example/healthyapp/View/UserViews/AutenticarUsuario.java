package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthyapp.Model.UserObjects.VerificacionUsuario;
import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.VerificacionViewModel;

public class AutenticarUsuario extends AppCompatActivity implements View.OnClickListener {

    private EditText verifyText;
    private Button okButton;
    private VerificacionViewModel verificacionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticar_usuario);

        verifyText = findViewById(R.id.verifyCode);
        okButton = findViewById(R.id.ok2);

        findViewById(R.id.atras).setOnClickListener(this);
        verificacionViewModel = new ViewModelProvider(this).get(VerificacionViewModel.class);

        okButton.setOnClickListener(view -> {
            String verificationCode = verifyText.getText().toString().trim();
            String email = getIntent().getStringExtra("email");
            String password = getIntent().getStringExtra("password");

            VerificacionUsuario verificacionUsuario = new VerificacionUsuario(email,password,verificationCode);
            verificacionViewModel.verifyUser(verificacionUsuario).observe(this, verificacionResponse -> {
                if (verificacionResponse != null) {
                    if (verificacionResponse.getStatusCode() == 200) {
                        Toast.makeText(this, "Cuenta verificada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoginView.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, verificacionResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}