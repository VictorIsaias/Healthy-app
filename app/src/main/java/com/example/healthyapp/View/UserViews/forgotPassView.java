package com.example.healthyapp.View.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healthyapp.R;
import com.example.healthyapp.ViewModel.ForgotPassViewModel;

import java.io.Serializable;

public class forgotPassView extends AppCompatActivity implements View.OnClickListener {

    ForgotPassViewModel viewModel;
    ImageButton backbutton;
    Button send;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_view);
        findViewById(R.id.atras).setOnClickListener(this);
        backbutton = findViewById(R.id.atras);
        findViewById(R.id.send).setOnClickListener(this);
        edit=findViewById(R.id.emailtext);
        Activity a=this;

        viewModel = new ViewModelProvider(this).get(ForgotPassViewModel.class);
        viewModel.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Toast.makeText(forgotPassView.this, response, Toast.LENGTH_SHORT).show();
                if (!response.startsWith("error")) {
                    a.startActivity(new Intent(a, CodePasswordView.class));
                    a.finish();
                }
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(forgotPassView.this, LoginView.class);
                startActivity(backintent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.atras){
            this.finish();
        } else if (view.getId()==R.id.send) {
            viewModel.sendMail(getTokenShared(),edit.getText().toString());
            Intent intent = new Intent(this,CodePasswordView.class);
            intent.putExtra("email", edit.getText().toString());
            this.startActivity(intent);
            this.finish();
        }
    }
    private String getTokenShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
}