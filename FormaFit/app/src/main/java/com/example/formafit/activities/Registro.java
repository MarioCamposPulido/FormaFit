package com.example.formafit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    private ImageButton maleButton, femaleButton;

    private Button nacimientoButtonRegistro, crearUsuarioButton;

    private TextView medidaPeso, pesoKg;

    private TextInputEditText nombreRegistro, emailRegistro, passwordRegistro;
    private TextInputLayout campoEmailRegistro, campoNombreRegistro, campoPasswordRegistro;
    private Slider sliderPesoRegistro, sliderAlturaRegistro;

    private BaseDatosHelper dbHelper;

    private static int crearAltura = 0;
    private static int crearPeso = 0;
    private static boolean fechaIntroducida = false;

    public int calcularEdad(String fechaNacimientoStr) {
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
            return -1; // o alguna otra indicación de error
        }
    }

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
        campoEmailRegistro = findViewById(R.id.campoEmailRegistro);
        campoNombreRegistro = findViewById(R.id.campoNombreRegistro);
        campoPasswordRegistro = findViewById(R.id.campoPasswordRegistro);

        maleButton.setSelected(true);


        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (femaleButton.isSelected()) {
                    femaleButton.setSelected(false);
                } else if (!maleButton.isSelected()) {
                    maleButton.setSelected(true);
                }
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (maleButton.isSelected()) {
                    maleButton.setSelected(false);
                }else if (!femaleButton.isSelected()) {
                    femaleButton.setSelected(true);
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

                    if (calcularEdad(formattedDate) != -1){
                        nacimientoButtonRegistro.setText(formattedDate);
                        nacimientoButtonRegistro.setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
                        fechaIntroducida = true;
                    }else {
                        nacimientoButtonRegistro.setText(getResources().getString(R.string.fechaNoValida));
                        nacimientoButtonRegistro.setTextColor(ContextCompat.getColor(view.getContext(), R.color.buttons_color_verde));
                    }

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
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (emailRegistro.getText().toString().matches(emailPattern)) {
                    campoEmailRegistro.setErrorEnabled(false);
                    if (!dbHelper.checkEmail(emailRegistro.getText().toString())){
                        campoEmailRegistro.setErrorEnabled(false);
                        if (!nombreRegistro.getText().toString().isEmpty()) {
                            campoNombreRegistro.setErrorEnabled(false);
                            if (!passwordRegistro.getText().toString().isEmpty()) {
                                campoPasswordRegistro.setErrorEnabled(false);
                                if (crearPeso != 0) {
                                    if (crearAltura != 0) {
                                        if (fechaIntroducida){
                                            dbHelper.insertNewUserRegistro(emailRegistro.getText().toString(), passwordRegistro.getText().toString(), nombreRegistro.getText().toString(),
                                                    getGeneroLogin(), nacimientoButtonRegistro.getText().toString(), crearAltura, getFechaActual(), crearPeso);
                                            crearPeso = 0;
                                            crearAltura = 0;
                                            fechaIntroducida = false;
                                            openLogin(emailRegistro.getText().toString(), passwordRegistro.getText().toString());
                                        }else {
                                            nacimientoButtonRegistro.setText(getResources().getString(R.string.introduceFecha));
                                            nacimientoButtonRegistro.setTextColor(ContextCompat.getColor(view.getContext(), R.color.buttons_color_verde));
                                        }
                                    } else {
                                        medidaPeso.setText(getResources().getString(R.string.introduceAltura));
                                        medidaPeso.setTextColor(ContextCompat.getColor(view.getContext(), R.color.verdeMasOscuro));
                                    }
                                } else {
                                    pesoKg.setText(getResources().getString(R.string.introducePeso));
                                    pesoKg.setTextColor(ContextCompat.getColor(view.getContext(), R.color.buttons_color_rosa));
                                }
                            } else {
                                campoPasswordRegistro.setError(getResources().getString(R.string.campoVacio));
                            }
                        } else {
                            campoNombreRegistro.setError(getResources().getString(R.string.campoVacio));
                        }
                    }else {
                        campoEmailRegistro.setError(getResources().getString(R.string.usuarioExiste));
                    }
                } else {
                    campoEmailRegistro.setError(getResources().getString(R.string.correoNoValido));
                }
            }
        });


    }
}