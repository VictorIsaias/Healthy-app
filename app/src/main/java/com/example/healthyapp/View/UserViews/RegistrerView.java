package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healthyapp.Model.UserObjects.UsuarioRegistro;
import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.RegistroViewModel;

public class RegistrerView extends AppCompatActivity implements LifecycleOwner{

    private EditText emailEditText, passwordEditText, nameEditText, lastnameEditText;
    private Button boton;
    private ImageButton atras;
    private RegistroViewModel registroViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer_view);

        nameEditText = findViewById(R.id.nametext);
        lastnameEditText = findViewById(R.id.lastnametext);
        emailEditText = findViewById(R.id.emailtext);
        passwordEditText = findViewById(R.id.passwordtext);
        boton = findViewById(R.id.ok);
        atras = findViewById(R.id.atras);

        ImageButton eye=findViewById(R.id.eye);
                eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eye.setImageResource(R.drawable.eye_closed);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eye.setImageResource(R.drawable.eye);
                }
            }
        });



        registroViewModel = new ViewModelProvider(this).get(RegistroViewModel.class);
        registroViewModel.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(RegistrerView.this, response, Toast.LENGTH_SHORT).show();
            }
        });
        boton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString().trim();
            String lastname = lastnameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            UsuarioRegistro usuarioRegistro = new UsuarioRegistro(name,lastname,email,password);
            registroViewModel.registrarUsuario(usuarioRegistro);
        });

        registroViewModel.getRegistroLiveData().observe(this, registroResponse -> {
            if (registroResponse != null) {
                Toast.makeText(this, "Registro exitoso, revisa tu correo para ver tu código", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AutenticarUsuario.class);
                intent.putExtra("email", emailEditText.getText().toString().trim());
                intent.putExtra("password", passwordEditText.getText().toString().trim());
                startActivity(intent);
                //finish();
                //Poner finish ni no se quiere volver ni usar esta activity en segundo plano
            }
            else {
                Toast.makeText(this, "Algo salió mal, intenta de nuevo.", Toast.LENGTH_SHORT).show();
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(RegistrerView.this, LoginView.class);
                startActivity(backIntent);

            }
        });
    }
}