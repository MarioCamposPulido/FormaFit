package com.example.formafit.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Class Login
 * Inicia sesión y comprueba los datos, con esta clase también será posible cambiar al
 * Activity Registro para registrarse
 */
public class Login extends AppCompatActivity {

    private BaseDatosHelper dbHelper;
    private TextView emailLogin, passwordLogin;
    private TextInputLayout campoEmailLogin, campoPasswordLogin;

    /**
     * Abre una actividad
     * @param context Referencia al entorno actual
     * @param clase Clase para cambiar a ese Activity
     */
    private void openActivities(Context context, Class<?> clase) {
        Intent intent = new Intent(context, clase);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Deshabilita el botón de retroceso
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // No hacer nada para deshabilitar el botón de retroceso
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

        Button loginButton = findViewById(R.id.loginButton);
        Button registroButton = findViewById(R.id.registroButton);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        campoEmailLogin = findViewById(R.id.campoEmailLogin);
        campoPasswordLogin = findViewById(R.id.campoPasswordLogin);

        // Obtener el Intent que inició esta actividad
        // Esto servirá cuando te registres, los datos se colocarán en el Login
        Intent intent = getIntent();
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

        // Verifica si los datos introducidos son correctos
        loginButton.setOnClickListener(view -> {
            dbHelper = new BaseDatosHelper(getBaseContext());
            if (dbHelper.checkUserLogin(emailLogin.getText().toString(), passwordLogin.getText().toString())) {
                campoEmailLogin.setErrorEnabled(false);
                campoPasswordLogin.setErrorEnabled(false);
                openActivities(getBaseContext(), MainActivity.class);
                MainActivity.email = emailLogin.getText().toString();
            } else {
                campoEmailLogin.setError(getResources().getText(R.string.emailOPasswordMal));
                campoPasswordLogin.setError(getResources().getText(R.string.emailOPasswordMal));
            }
        });

        // Abre el Activity Registro
        registroButton.setOnClickListener(view -> openActivities(getBaseContext(), Registro.class));


    }
}