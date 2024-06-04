package com.example.formafit.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.adapter.AdapterRecyclerViewDiario;
import com.example.formafit.base_datos.BaseDatosHelper;

/**
 * Class DiarioFragment
 * Muestra todas las entradas de peso con info adicional
 */
public class DiarioFragment extends Fragment {

    RecyclerView recyclerDiario;
    BaseDatosHelper dbHelper;
    ImageButton eliminarTodasEntradasPesoDiario;

    public DiarioFragment() {
        // Required empty public constructor
    }

    private void showCustomDialogEliminarAllEntradasPeso() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_borrar_peso, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout)
                .setTitle(null)
                .setPositiveButton(getResources().getText(R.string.aceptar), (dialog, id) -> {
                    dbHelper.deleteAllEntradasPesoUserExceptLast(MainActivity.email);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new DiarioFragment()).commit();
                })
                .setNegativeButton(getResources().getText(R.string.cancelar), (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
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
        View view =  inflater.inflate(R.layout.fragment_diario, container, false);

        recyclerDiario = view.findViewById(R.id.recyclerDiario);
        eliminarTodasEntradasPesoDiario = view.findViewById(R.id.eliminarTodasEntradasPesoDiario);

        dbHelper = new BaseDatosHelper(getContext());

        recyclerDiario.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerDiario.setAdapter(new AdapterRecyclerViewDiario(dbHelper.getAllEntradasPeso(MainActivity.email)));

        eliminarTodasEntradasPesoDiario.setOnClickListener(view1 -> showCustomDialogEliminarAllEntradasPeso());

        return view;
    }
}