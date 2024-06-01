package com.example.formafit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.google.android.material.imageview.ShapeableImageView;

public class CrearDesafioFragment extends Fragment {

    private TextView tituloCrearDesafio, descripcionCrearDesafio;
    private ShapeableImageView imgCrearDesafio;
    private ImageButton confirmarCrearDesafioButton;
    private BaseDatosHelper dbHelper;
    private Bitmap imgDesafio = null;

    private final ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imgBitmap = (Bitmap) extras.get("data");
                        imgCrearDesafio.setImageBitmap(imgBitmap);
                        imgDesafio = imgBitmap;
                    }
                }
            });

    public CrearDesafioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crear_desafio, container, false);

        tituloCrearDesafio = view.findViewById(R.id.tituloCrearDesafio);
        descripcionCrearDesafio = view.findViewById(R.id.descripcionCrearDesafio);
        imgCrearDesafio = view.findViewById(R.id.imgCrearDesafio);
        confirmarCrearDesafioButton = view.findViewById(R.id.confirmarCrearDesafioButton);

        imgCrearDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        confirmarCrearDesafioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new BaseDatosHelper(getContext());
                dbHelper.insertNewDesafio(imgDesafio, tituloCrearDesafio.getText().toString(), descripcionCrearDesafio.getText().toString());
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new DesafiosFragment()).commit();
            }
        });



        return view;
    }
}