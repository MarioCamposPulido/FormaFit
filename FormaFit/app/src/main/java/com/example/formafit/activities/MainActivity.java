package com.example.formafit.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.example.formafit.fragments.AjustesFragment;
import com.example.formafit.R;
import com.example.formafit.fragments.BasculaFragment;
import com.example.formafit.fragments.DesafiosFragment;
import com.example.formafit.fragments.ImcFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Class MainActivity
 * Ejecuta la gran mayoría de los fragmentos aquí,
 * también contiene métodos utilizados por otras clases,
 * contiene el email del usuario que inició sesión, esto servirá para
 * hacer futuras consultas y otras acciones con el email del usuario
 */
public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    // Email del usuario que inició sesión
    public static String email;

    // Calcula la edad y devuelve el número de años
    public static int calcularEdad(String fechaNacimientoStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parsear la fecha de nacimiento
            Date fechaNacimiento = sdf.parse(fechaNacimientoStr);

            // Obtener la fecha actual
            Calendar fechaActual = Calendar.getInstance();

            // Crear un calendario con la fecha de nacimiento
            Calendar nacimiento = Calendar.getInstance();
            nacimiento.setTime(Objects.requireNonNull(fechaNacimiento));

            // Calcular la edad
            int edad = fechaActual.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);

            // Comprobar si el cumpleaños ya ha pasado este año
            if (fechaActual.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--;
            }

            return edad;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Devuelve la fecha actual
    public static String getFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return formatoFecha.format(fechaActual);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        // Deshabilita el botón de retroceso
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // No hacer nada para deshabilitar el botón de retroceso
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Configuración de la barra de navegación
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int idItemn = item.getItemId();

            if (idItemn == R.id.bascula) {
                fragment = new BasculaFragment();
            }

            if (idItemn == R.id.imc) {
                fragment = new ImcFragment();
            }

            if (idItemn == R.id.ajustes) {
                fragment = new AjustesFragment();
            }

            if (idItemn == R.id.desafios) {
                fragment = new DesafiosFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }

            return true;
        });
    }
}