package com.example.formafit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;

public class Login extends AppCompatActivity {

    private BaseDatosHelper dbHelper;
    private Button loginButton, registroButton;

    private TextView emailLogin, passwordLogin;


    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openRegistro(){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        registroButton = findViewById(R.id.registroButton);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new BaseDatosHelper(getBaseContext());
                // Comprueba si el usuario existe en la base de datos, si es cierto iniciará sesión
                if (dbHelper.checkUserLogin(emailLogin.getText().toString(), passwordLogin.getText().toString())){
                    openMainActivity();
                }else{
                    Toast.makeText(Login.this, "caca", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistro();
            }
        });



    }
}