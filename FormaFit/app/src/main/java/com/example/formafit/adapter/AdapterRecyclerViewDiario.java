package com.example.formafit.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.example.formafit.java.EntradaPeso;

import java.util.LinkedList;
import java.util.Objects;

public class AdapterRecyclerViewDiario extends RecyclerView.Adapter<AdapterRecyclerViewDiario.EntradasPesosViewHolder> {

    private LinkedList<EntradaPeso> entradaPesos;


    public AdapterRecyclerViewDiario (LinkedList<EntradaPeso> entradaPesoRecyclers){
        this.entradaPesos = entradaPesoRecyclers;
    };


    @NonNull
    @Override
    public EntradasPesosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_diario, parent, false);
        return new EntradasPesosViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EntradasPesosViewHolder holder, int position) {
        holder.bind(entradaPesos.get(position));
    }

    /**
     * Devuelve el número total de elementos de la lista
     * @return
     */
    @Override
    public int getItemCount() {
        return entradaPesos.size();
    }

    /**
     * Clase para referirse a la celda personalizada del recycler
     */
    class EntradasPesosViewHolder extends RecyclerView.ViewHolder{

        private TextView fechaEntradaPeso, grasasPorcentajeDiario, imcCalculadoDiario, kilosDiario;
        private ImageView img;



        /**
         * Declaramos lo que es cada item, para interactuar con él
         * @param itemView
         */
        public EntradasPesosViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fechaEntradaPeso = itemView.findViewById(R.id.fechaEntradaPeso);
            this.img = itemView.findViewById(R.id.diarioImagen);
            this.grasasPorcentajeDiario = itemView.findViewById(R.id.grasasPorcentajeDiario);
            this.imcCalculadoDiario = itemView.findViewById(R.id.imcCalculadoDiario);
            this.kilosDiario = itemView.findViewById(R.id.kilosDiario);
        }


        public void bind(EntradaPeso entradaPeso){
                fechaEntradaPeso.setText(entradaPeso.getFecha());
                if (!Objects.isNull(entradaPeso.getImagen())){
                    img.setImageBitmap(entradaPeso.getImagen());
                }
                grasasPorcentajeDiario.setText("[" + entradaPeso.getPorcentajeGrasa() + " %]");
                imcCalculadoDiario.setText("{ " + entradaPeso.getImc() + " }");
                kilosDiario.setText(entradaPeso.getPeso() + " kg");

//            if (!Objects.isNull(usuario.getGenero())){
//                descripcionUsuario.setText(usuario.getGenero());
//            }
//            if (!Objects.isNull(usuario.getImagenPerfil())){
//                fotoUsuario.setImageBitmap(usuario.getImagenPerfil());
//            }
//
//            seguirButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dbHelper = new BaseDatosHelper(itemView.getContext());
//                    dbHelper.upgradeSeguidores(usuario.getEmail());
//
//                    Toast.makeText(itemView.getContext(), itemView.getContext().getResources().getString(R.string.seguisteA) + " " +
//                            usuario.getEmail(), Toast.LENGTH_SHORT).show();
//                    dbHelper.close();
//                }
//            });
        }
    }
}

