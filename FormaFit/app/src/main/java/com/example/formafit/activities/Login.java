package com.example.formafit.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        // Crear un callback que no hace nada para deshabilitar el botón de retroceso
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // No hacer nada para deshabilitar el botón de retroceso
            }
        };

        // Añadir el callback al dispatcher de onBackPressed
        getOnBackPressedDispatcher().addCallback(this, callback);

        loginButton = findViewById(R.id.loginButton);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        registroButton = findViewById(R.id.registroButton);

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();

        // Extraer los datos del Intent
        if (intent != null) {
            if (intent.hasExtra("EXTRA_EMAIL")) {
                String email = intent.getStringExtra("EXTRA_EMAIL");
                emailLogin.setText(email);
            }
            if (intent.hasExtra("EXTRA_PASSWORD")) {
                String password = intent.getStringExtra("EXTRA_PASSWORD");
                passwordLogin.setText(password);
            }
        }

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new BaseDatosHelper(getBaseContext());
                if (dbHelper.checkUserLogin(emailLogin.getText().toString(), passwordLogin.getText().toString())){
                    openMainActivity();
                    MainActivity.email = emailLogin.getText().toString();
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