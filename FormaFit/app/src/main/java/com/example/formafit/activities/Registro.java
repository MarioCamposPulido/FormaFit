package com.example.formafit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    private ImageButton maleButton, femaleButton;

    private Button nacimientoButtonRegistro, crearUsuarioButton;

    private TextView medidaPeso, pesoKg;

    private TextInputEditText nombreRegistro, emailRegistro, passwordRegistro;

    private Slider sliderPesoRegistro, sliderAlturaRegistro;

    private BaseDatosHelper dbHelper;

    private static int crearAltura;
    private static int crearPeso;

    private void openLogin(String email, String password) {
        Intent intent = new Intent(this, Login.class);

        // Agregar los textos al Intent como extras
        intent.putExtra("EXTRA_EMAIL", email);
        intent.putExtra("EXTRA_PASSWORD", password);

        startActivity(intent);
    }

    private String getGeneroLogin() {
        if (maleButton.isSelected()) {
            return "M";
        }
        if (femaleButton.isSelected()) {
            return "F";
        }
        return "";
    }

    private String getFechaActual() {
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return formatoFecha.format(fechaActual);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);


        maleButton = findViewById(R.id.maleButton);
        femaleButton = findViewById(R.id.femaleButton);
        nacimientoButtonRegistro = findViewById(R.id.nacimientoButtonRegistro);
        crearUsuarioButton = findViewById(R.id.crearUsuarioButton);
        nombreRegistro = findViewById(R.id.nombreRegistro);
        emailRegistro = findViewById(R.id.emailRegistro);
        passwordRegistro = findViewById(R.id.passwordRegistro);
        sliderAlturaRegistro = findViewById(R.id.sliderAlturaRegistro);
        sliderPesoRegistro = findViewById(R.id.sliderPesoRegistro);
        medidaPeso = findViewById(R.id.medidaPeso);
        pesoKg = findViewById(R.id.pesoKg);

        maleButton.setSelected(true);

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (femaleButton.isSelected()) {
                    femaleButton.setSelected(false);
                }
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (maleButton.isSelected()) {
                    maleButton.setSelected(false);
                }
            }
        });

        nacimientoButtonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.MyDatePickerTheme);
                MaterialDatePicker<Long> datePicker = builder.build();

                datePicker.addOnPositiveButtonClickListener(selection -> {
                    // La fecha seleccionada está en milisegundos desde la época (Unix time)
                    // Puedes convertirlo al formato deseado
                    Date date = new Date(selection);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = sdf.format(date);

                    // Hacer algo con la fecha seleccionada
                    nacimientoButtonRegistro.setText(formattedDate);
                });

                datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        sliderAlturaRegistro.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                medidaPeso.setText((int) value + " cm");
                crearAltura = (int) value;
            }
        });

        sliderPesoRegistro.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                pesoKg.setText((int) value + " kg");
                crearPeso = (int) value;
            }
        });

        crearUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new BaseDatosHelper(getBaseContext());
                dbHelper.insertNewUserRegistro(emailRegistro.getText().toString(), passwordRegistro.getText().toString(), nombreRegistro.getText().toString(),
                        getGeneroLogin(), nacimientoButtonRegistro.getText().toString(), crearAltura, getFechaActual(), crearPeso);
                openLogin(emailRegistro.getText().toString(), passwordRegistro.getText().toString());
            }
        });


    }
}