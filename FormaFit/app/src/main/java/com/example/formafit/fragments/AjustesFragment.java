package com.example.formafit.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.activities.Login;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.github.mikephil.charting.BuildConfig;

import java.util.Locale;

public class AjustesFragment extends Fragment {

    private TextView infoAjustes, idiomaAjustes, editarPerfilAjustes, cerrarSesionAjustes, eliminarCuentaAjustes;
    private RadioButton radioButtonEspanol, radioButtonIngles;
    private BaseDatosHelper dbHelper;

    private static String idiomaSeleccionado = "";

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, res.getDisplayMetrics());
        getActivity().recreate(); // Reiniciar la actividad para aplicar el nuevo idioma
    }

    private void showDialogVersion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.version) + ": " + BuildConfig.VERSION_NAME)
                .setTitle(null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDialogEliminarCuenta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.confirmacionEliminacion))
                .setTitle(getResources().getString(R.string.eliminarCuenta))
                .setPositiveButton(getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper = new BaseDatosHelper(getContext());
                        dbHelper.deleteAllDataUser(MainActivity.email);
                        MainActivity.email = null;
                        startActivity(new Intent(getActivity(), Login.class));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialogIdioma() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_idioma, null);
        radioButtonEspanol = dialogLayout.findViewById(R.id.radioButtonEspanol);
        radioButtonIngles = dialogLayout.findViewById(R.id.radioButtonIngles);

        radioButtonEspanol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idiomaSeleccionado = "es";
                }
            }
        });

        radioButtonIngles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idiomaSeleccionado = "en";
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout)
                .setTitle(null)
                .setPositiveButton(getResources().getText(R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setLocale(idiomaSeleccionado);
                    }
                })
                .setNegativeButton(getResources().getText(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public AjustesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        infoAjustes = view.findViewById(R.id.infoAjustes);
        idiomaAjustes = view.findViewById(R.id.idiomaAjustes);
        editarPerfilAjustes = view.findViewById(R.id.editarPerfilAjustes);
        cerrarSesionAjustes = view.findViewById(R.id.cerrarSesionAjustes);
        eliminarCuentaAjustes = view.findViewById(R.id.eliminarCuentaAjustes);

        editarPerfilAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new EditarUserFragment()).commit();
            }
        });

        infoAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogVersion();
            }
        });

        idiomaAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogIdioma();
            }
        });

        cerrarSesionAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.email = null;
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        eliminarCuentaAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEliminarCuenta();
            }
        });

        return view;
    }
}