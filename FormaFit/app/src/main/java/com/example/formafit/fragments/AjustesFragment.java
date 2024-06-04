package com.example.formafit.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.formafit.R;
import com.example.formafit.activities.Login;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.github.mikephil.charting.BuildConfig;

import java.util.Locale;

/**
 * Class AjustesFragment
 * Diferentes herramientas para cambiar la interfaz y
 * editar los usuarios, tambien está la version del proyecto
 */
public class AjustesFragment extends Fragment {

    private BaseDatosHelper dbHelper;

    private static String idiomaSeleccionado = "";

    // Actualiza la app para ver el otro idioma
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, res.getDisplayMetrics());
        requireActivity().recreate(); // Reiniciar la actividad para aplicar el nuevo idioma
    }

    // Muestra el dialog de la versión
    private void showDialogVersion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.version) + ": " + BuildConfig.VERSION_NAME)
                .setTitle(null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Muestra el dialog para eliminar una cuenta
    public void showDialogEliminarCuenta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.confirmacionEliminacion))
                .setTitle(getResources().getString(R.string.eliminarCuenta))
                .setPositiveButton(getResources().getString(R.string.aceptar), (dialog, id) -> {
                    dbHelper = new BaseDatosHelper(getContext());
                    dbHelper.deleteAllDataUser(MainActivity.email);
                    MainActivity.email = null;
                    startActivity(new Intent(getActivity(), Login.class));
                })
                .setNegativeButton(getResources().getString(R.string.cancelar), (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Muestra el dialogo del idioma
    private void showDialogIdioma() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_idioma, null);
        RadioButton radioButtonEspanol = dialogLayout.findViewById(R.id.radioButtonEspanol);
        RadioButton radioButtonIngles = dialogLayout.findViewById(R.id.radioButtonIngles);

        radioButtonEspanol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                idiomaSeleccionado = "es";
            }
        });

        radioButtonIngles.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                idiomaSeleccionado = "en";
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout)
                .setTitle(null)
                .setPositiveButton(getResources().getText(R.string.aceptar), (dialog, id) -> setLocale(idiomaSeleccionado))
                .setNegativeButton(getResources().getText(R.string.cancelar), (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public AjustesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        TextView infoAjustes = view.findViewById(R.id.infoAjustes);
        TextView idiomaAjustes = view.findViewById(R.id.idiomaAjustes);
        TextView editarPerfilAjustes = view.findViewById(R.id.editarPerfilAjustes);
        TextView cerrarSesionAjustes = view.findViewById(R.id.cerrarSesionAjustes);
        TextView eliminarCuentaAjustes = view.findViewById(R.id.eliminarCuentaAjustes);

        editarPerfilAjustes.setOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new EditarUserFragment()).commit()
        );

        infoAjustes.setOnClickListener(view12 -> showDialogVersion());

        idiomaAjustes.setOnClickListener(view13 -> showDialogIdioma());

        cerrarSesionAjustes.setOnClickListener(view14 -> {
            MainActivity.email = null;
            startActivity(new Intent(getActivity(), Login.class));
        });

        eliminarCuentaAjustes.setOnClickListener(view15 -> showDialogEliminarCuenta());

        return view;
    }
}