package com.example.formafit.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.adapter.AdapterRecyclerViewDesafios;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.button.MaterialButton;

public class DesafiosFragment extends Fragment {

    BaseDatosHelper dbHelper;
    RecyclerView recyclerDesafios;
    MaterialButton desafiosCrearDesafio, desafiosCambioButton;

    public DesafiosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_desafios, container, false);

        recyclerDesafios = view.findViewById(R.id.recyclerDesafios);
        desafiosCrearDesafio = view.findViewById(R.id.desafiosCrearDesafio);
        desafiosCambioButton = view.findViewById(R.id.desafiosCambioButton);

        dbHelper = new BaseDatosHelper(getContext());

        recyclerDesafios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerDesafios.setAdapter(new AdapterRecyclerViewDesafios(dbHelper.getAllDesafiosActualesUser(MainActivity.email)));

        desafiosCrearDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new CrearDesafioFragment()).commit();
            }
        });

        desafiosCambioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (!view.isSelected()){
                        desafiosCambioButton.setText(getResources().getString(R.string.cambiarDesafiosActuales));
                        desafiosCambioButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_medalla_conseguida));
                        recyclerDesafios.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerDesafios.setAdapter(new AdapterRecyclerViewDesafios(dbHelper.getAllDesafiosCompletadosUser(MainActivity.email)));
                        view.setSelected(true);
                    }else {
                        desafiosCambioButton.setText(getResources().getString(R.string.cambiarDesafiosCompletados));
                        desafiosCambioButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_persona_celebrando));
                        recyclerDesafios.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerDesafios.setAdapter(new AdapterRecyclerViewDesafios(dbHelper.getAllDesafiosActualesUser(MainActivity.email)));
                        view.setSelected(false);
                    }
            }
        });

        return view;
    }
}