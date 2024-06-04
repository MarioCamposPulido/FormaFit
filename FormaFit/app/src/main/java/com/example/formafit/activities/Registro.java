package com.example.formafit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Class Registro
 * Crea nuevos usuarios y comprueba que los datos introducidos sean válidos
 */
public class Registro extends AppCompatActivity {

    private ImageButton maleButton, femaleButton;
    private Button nacimientoButtonRegistro;
    private TextView medidaPeso, pesoKg;
    private TextInputEditText nombreRegistro, emailRegistro, passwordRegistro;
    private TextInputLayout campoEmailRegistro, campoNombreRegistro, campoPasswordRegistro;
    private BaseDatosHelper dbHelper;

    private static int crearAltura;
    private static int crearPeso;
    private static boolean fechaIntroducida;

    // Abre el Activity Login y comparte los datos que inician sesión
    private void openLogin(String email, String password) {
        Intent intent = new Intent(this, Login.class);

        // Agregar los textos al Intent como extras
        intent.putExtra("EXTRA_EMAIL", email);
        intent.putExtra("EXTRA_PASSWORD", password);

        startActivity(intent);
    }

    // Dependiendo del icono que seleccione el usuario será hombre (M) o mujer (F)
    // Retorna un String para pasarlo a la base de datos
    private String getGeneroLogin() {
        if (maleButton.isSelected()) {
            return "M";
        }
        if (femaleButton.isSelected()) {
            return "F";
        }
        return "";
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
        Button crearUsuarioButton = findViewById(R.id.crearUsuarioButton);
        nombreRegistro = findViewById(R.id.nombreRegistro);
        emailRegistro = findViewById(R.id.emailRegistro);
        passwordRegistro = findViewById(R.id.passwordRegistro);
        Slider sliderAlturaRegistro = findViewById(R.id.sliderAlturaRegistro);
        Slider sliderPesoRegistro = findViewById(R.id.sliderPesoRegistro);
        medidaPeso = findViewById(R.id.medidaPeso);
        pesoKg = findViewById(R.id.pesoKg);
        campoEmailRegistro = findViewById(R.id.campoEmailRegistro);
        campoNombreRegistro = findViewById(R.id.campoNombreRegistro);
        campoPasswordRegistro = findViewById(R.id.campoPasswordRegistro);

        crearPeso = 0;
        crearAltura = 0;
        fechaIntroducida = false;

        maleButton.setSelected(true);

        maleButton.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (femaleButton.isSelected()) {
                femaleButton.setSelected(false);
            } else if (!maleButton.isSelected()) {
                maleButton.setSelected(true);
            }
        });

        femaleButton.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            if (maleButton.isSelected()) {
                maleButton.setSelected(false);
            } else if (!femaleButton.isSelected()) {
                femaleButton.setSelected(true);
            }
        });

        // Llama al DatePicker para seleccionar una fecha válida
        nacimientoButtonRegistro.setOnClickListener(view -> {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.MyDatePickerTheme);
            MaterialDatePicker<Long> datePicker = builder.build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Date date = new Date(selection);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = sdf.format(date);

                if (MainActivity.calcularEdad(formattedDate) != -1) {
                    nacimientoButtonRegistro.setText(formattedDate);
                    nacimientoButtonRegistro.setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
                    fechaIntroducida = true;
                } else {
                    nacimientoButtonRegistro.setText(getResources().getString(R.string.fechaNoValida));
                    nacimientoButtonRegistro.setTextColor(ContextCompat.getColor(view.getContext(), R.color.buttons_color_verde));
                }

            });
            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });

        sliderAlturaRegistro.addOnChangeListener((slider, value, fromUser) -> {
            String textoAltura = (int) value + " cm";
            medidaPeso.setText(textoAltura);
            crearAltura = (int) value;
        });

        sliderPesoRegistro.addOnChangeListener((slider, value, fromUser) -> {
            String textoPeso = (int) value + " kg";
            pesoKg.setText(textoPeso);
            crearPeso = (int) value;
        });

        // Comprueba todos los datos introducidos por el nuevo usuario
        crearUsuarioButton.setOnClickListener(view -> {
            dbHelper = new BaseDatosHelper(getBaseContext());
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (Objects.requireNonNull(emailRegistro.getText()).toString().matches(emailPattern)) {
                campoEmailRegistro.setErrorEnabled(false);
                if (!dbHelper.checkEmail(emailRegistro.getText().toString())) {
                    campoEmailRegistro.setErrorEnabled(false);
                    if (!Objects.requireNonNull(nombreRegistro.getText()).toString().isEmpty()) {
                        campoNombreRegistro.setErrorEnabled(false);
                        if (!Objects.requireNonNull(passwordRegistro.getText()).toString().isEmpty()) {
                            campoPasswordRegistro.setErrorEnabled(false);
                            if (crearPeso != 0) {
                                if (crearAltura != 0) {
                                    if (fechaIntroducida) {
                                        dbHelper.insertNewUserRegistro(emailRegistro.getText().toString(), passwordRegistro.getText().toString(), nombreRegistro.getText().toString(),
                                                getGeneroLogin(), nacimientoButtonRegistro.getText().toString(), crearAltura, MainActivity.getFechaActual(), crearPeso);
                                        openLogin(emailRegistro.getText().toString(), passwordRegistro.getText().toString());
                                    } else {
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
                } else {
                    campoEmailRegistro.setError(getResources().getString(R.string.usuarioExiste));
                }
            } else {
                campoEmailRegistro.setError(getResources().getString(R.string.correoNoValido));
            }
        });
    }

}