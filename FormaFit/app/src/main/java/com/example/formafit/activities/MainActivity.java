package com.example.formafit.activities;

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

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    public static String email;

    public static int calcularEdad(String fechaNacimientoStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parsear la fecha de nacimiento
            Date fechaNacimiento = sdf.parse(fechaNacimientoStr);

            // Obtener la fecha actual
            Calendar fechaActual = Calendar.getInstance();

            // Crear un calendario con la fecha de nacimiento
            Calendar nacimiento = Calendar.getInstance();
            nacimiento.setTime(fechaNacimiento);

            // Calcular la edad
            int edad = fechaActual.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);

            // Comprobar si el cumpleaños ya ha pasado este año
            if (fechaActual.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--;
            }

            return edad;
        } catch (ParseException e) {
            // Manejar la excepción si el formato de la fecha es incorrecto
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

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