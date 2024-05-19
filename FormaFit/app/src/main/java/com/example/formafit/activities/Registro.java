package com.example.formafit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.formafit.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    private ImageButton maleButton, femaleButton;

    private Button nacimientoButton;

    private TextView crearPerfilTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Cambiar la barra de color de arriba en negro
        getWindow().setStatusBarColor(Color.BLACK);

        maleButton = findViewById(R.id.maleButton);
        femaleButton = findViewById(R.id.femaleButton);
        nacimientoButton = findViewById(R.id.nacimientoButton);
        crearPerfilTextView = findViewById(R.id.crearPerfilTextView);

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (femaleButton.isSelected()){
                    femaleButton.setSelected(false);
                }
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (maleButton.isSelected()){
                    maleButton.setSelected(false);
                }
            }
        });

        nacimientoButton.setOnClickListener(new View.OnClickListener() {
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
                    nacimientoButton.setText(formattedDate);
                });

                datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        // Para subrrayar texto
        crearPerfilTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


    }
}