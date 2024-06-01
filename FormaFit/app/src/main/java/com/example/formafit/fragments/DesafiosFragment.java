package com.example.formafit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.adapter.AdapterRecyclerViewDesafios;
import com.example.formafit.adapter.AdapterRecyclerViewDiario;
import com.example.formafit.base_datos.BaseDatosHelper;

public class DesafiosFragment extends Fragment {

    BaseDatosHelper dbHelper;
    RecyclerView recyclerDesafios;
    Button desafiosCrearDesafio;

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

        dbHelper = new BaseDatosHelper(getContext());

        recyclerDesafios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerDesafios.setAdapter(new AdapterRecyclerViewDesafios(dbHelper.getAllDesafiosUser(MainActivity.email)));

        desafiosCrearDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new CrearDesafioFragment()).commit();
            }
        });

        return view;
    }
}