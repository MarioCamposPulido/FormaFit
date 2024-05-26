package com.example.formafit.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.adapter.AdapterRecyclerViewDiario;
import com.example.formafit.base_datos.BaseDatosHelper;

public class DiarioFragment extends Fragment {

    RecyclerView recyclerDiario;
    BaseDatosHelper dbHelper;

    public DiarioFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_diario, container, false);

        recyclerDiario = view.findViewById(R.id.recyclerDiario);

        dbHelper = new BaseDatosHelper(getContext());

        recyclerDiario.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerDiario.setAdapter(new AdapterRecyclerViewDiario(dbHelper.getAllEntradasPeso(MainActivity.email)));



        return view;
    }
}