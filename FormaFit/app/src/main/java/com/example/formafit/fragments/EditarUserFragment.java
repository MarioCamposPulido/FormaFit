package com.example.formafit.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditarUserFragment extends Fragment {

    private Slider sliderAlturaEditarUser;
    private TextView medidaEditarUser, userNameEditarUser, emailEditarUser, passwordEditarUser;
    private Button nacimientoButtonEditarUser;
    private ImageButton femaleButtonEditarUser, maleButtonEdiartUser, confirmButtonEdiatPerfil;
    private BottomNavigationView bottomNavigation;
    private static int editarAltura = 0;
    private BaseDatosHelper dbHelper;

    private String getGeneroLogin() {
        if (maleButtonEdiartUser.isSelected()) {
            return "M";
        }
        if (femaleButtonEditarUser.isSelected()) {
            return "F";
        }
        return "";
    }

    public EditarUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new AjustesFragment()).commit();
            }
        };

        // Añadir el callback al dispatcher de onBackPressed
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editar_user, container, false);

        sliderAlturaEditarUser = view.findViewById(R.id.sliderAlturaEditarUser);
        medidaEditarUser = view.findViewById(R.id.medidaEditarUser);
        femaleButtonEditarUser = view.findViewById(R.id.femaleButtonEditarUser);
        maleButtonEdiartUser = view.findViewById(R.id.maleButtonEdiartUser);
        nacimientoButtonEditarUser = view.findViewById(R.id.nacimientoButtonEditarUser);
        confirmButtonEdiatPerfil = view.findViewById(R.id.confirmButtonEdiatPerfil);
        userNameEditarUser = view.findViewById(R.id.userNameEditarUser);
        emailEditarUser = view.findViewById(R.id.emailEditarUser);
        passwordEditarUser = view.findViewById(R.id.passwordEditarUser);


        maleButtonEdiartUser.setSelected(true);

        sliderAlturaEditarUser.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                medidaEditarUser.setText((int) value + " cm");
                editarAltura = (int) value;
            }
        });

        nacimientoButtonEditarUser.setOnClickListener(new View.OnClickListener() {
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
                    nacimientoButtonEditarUser.setText(formattedDate);
                });

                datePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        maleButtonEdiartUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (femaleButtonEditarUser.isSelected()) {
                    femaleButtonEditarUser.setSelected(false);
                }
            }
        });

        femaleButtonEditarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (maleButtonEdiartUser.isSelected()) {
                    maleButtonEdiartUser.setSelected(false);
                }
            }
        });

        confirmButtonEdiatPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new BaseDatosHelper(getContext());
                if (dbHelper.upgradeEditarUser(userNameEditarUser.getText().toString(), emailEditarUser.getText().toString(), passwordEditarUser.getText().toString(),
                        nacimientoButtonEditarUser.getText().toString() ,getGeneroLogin(), editarAltura)){
                    MainActivity.email = emailEditarUser.getText().toString();
                    if (getActivity() != null) {
                        bottomNavigation = getActivity().findViewById(R.id.bottom_navigation);
                        bottomNavigation.setSelectedItemId(R.id.bascula);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BasculaFragment()).commit();
                    }
                }else {
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}