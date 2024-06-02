package com.example.formafit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EntradaPesoFragment extends Fragment {

    private ShapeableImageView photoButton;
    private TextView entradaPesoKg, imcCalculado, grasasPorcentaje;
    private TextInputEditText descripcionEntradaPeso;
    private Slider sliderPesoEntradaPeso;
    private Button fechaButtonEntradaPeso;
    private ImageButton introducirNuevoPeso;
    private BaseDatosHelper dbHelper;

    private static int pesoNuevo;
    private static Bitmap imgTomada;


    private final ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imgBitmap = (Bitmap) extras.get("data");
                        photoButton.setImageBitmap(imgBitmap);
                        imgTomada = imgBitmap;
                    }
                }
            });

    public EntradaPesoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BasculaFragment()).commit();
            }
        };

        // Añadir el callback al dispatcher de onBackPressed
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrada_peso, container, false);

        photoButton = view.findViewById(R.id.photoButton);
        entradaPesoKg = view.findViewById(R.id.entradaPesoKg);
        sliderPesoEntradaPeso = view.findViewById(R.id.sliderPesoEntradaPeso);
        fechaButtonEntradaPeso = view.findViewById(R.id.fechaButtonEntradaPeso);
        imcCalculado = view.findViewById(R.id.imcCalculado);
        grasasPorcentaje = view.findViewById(R.id.grasasPorcentaje);
        introducirNuevoPeso = view.findViewById(R.id.introducirNuevoPeso);
        descripcionEntradaPeso = view.findViewById(R.id.descripcionEntradaPeso);

        fechaButtonEntradaPeso.setOnClickListener(new View.OnClickListener() {
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
                    fechaButtonEntradaPeso.setText(formattedDate);
                });

                datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrimos la camara con este método
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        introducirNuevoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new BaseDatosHelper(getContext());
                if (!Objects.isNull(imgTomada)){
                    dbHelper.insertNewEntradaPeso(MainActivity.email, fechaButtonEntradaPeso.getText().toString(),
                            descripcionEntradaPeso.getText().toString(), imgTomada,
                            pesoNuevo);
                    imgTomada = null;
                }else {
                    dbHelper.insertNewEntradaPeso(MainActivity.email, fechaButtonEntradaPeso.getText().toString(),
                            descripcionEntradaPeso.getText().toString(), null,
                            pesoNuevo);
                }

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BasculaFragment()).commit();
            }
        });

        sliderPesoEntradaPeso.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                entradaPesoKg.setText((int) value + " kg");
                dbHelper = new BaseDatosHelper(getContext());
                double imc = Math.floor((value / Math.pow(dbHelper.getAltura(MainActivity.email) / 100.0, 2) * 10)) / 10;
                imcCalculado.setText("{" + imc + "}");


                switch (dbHelper.getGenero(MainActivity.email)){
                    case "M":
                        double grasaCorporalHombre = Math.floor((1.20 * imc + 0.23 * dbHelper.getEdadUser(MainActivity.email) - 16.2) * 10) / 10;
                        if (grasaCorporalHombre > 7 ){
                            grasasPorcentaje.setText("[" + grasaCorporalHombre + "]");
                        }else {
                            grasasPorcentaje.setText("[" + 7.0 + "]");
                        }
                        break;
                    case "F":
                        double grasaCorporalMujer = Math.floor((1.20 * imc + 0.23 * dbHelper.getEdadUser(MainActivity.email) - 5.4) * 10) / 10;
                        if (grasaCorporalMujer > 10 ){
                            grasasPorcentaje.setText("[" + grasaCorporalMujer + "]");
                        }else {
                            grasasPorcentaje.setText("[" + 10.0 + "]");
                        }
                        break;
                    default:
                        grasasPorcentaje.setText("[??.?]");
                        break;
                }

                pesoNuevo = (int) value;
            }
        });

        return view;
    }
}