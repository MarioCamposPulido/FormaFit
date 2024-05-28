package com.example.formafit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.example.formafit.fragments.AjustesFragment;
import com.example.formafit.R;
import com.example.formafit.fragments.BasculaFragment;
import com.example.formafit.fragments.ImcFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    public static String email;

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

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }

            return true;
        });
    }
}