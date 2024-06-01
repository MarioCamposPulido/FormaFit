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
import androidx.core.content.ContextCompat;
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

        int diferenciaPeso = 0;
        if (position > 0) {
            int entradaPesoPrevia = entradaPesos.get(position - 1).getPeso();
            diferenciaPeso = entradaPesos.get(position).getPeso() - entradaPesoPrevia;
        }

        holder.bind(entradaPesos.get(position), diferenciaPeso);
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

        private TextView fechaEntradaPeso, grasasPorcentajeDiario, imcCalculadoDiario, kilosDiario, diferenciaPesoDiario, comentariosDiario;
        private ImageView diarioImagen;



        /**
         * Declaramos lo que es cada item, para interactuar con él
         * @param itemView
         */
        public EntradasPesosViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fechaEntradaPeso = itemView.findViewById(R.id.fechaEntradaPeso);
            this.diarioImagen = itemView.findViewById(R.id.diarioImagen);
            this.grasasPorcentajeDiario = itemView.findViewById(R.id.grasasPorcentajeDiario);
            this.imcCalculadoDiario = itemView.findViewById(R.id.imcCalculadoDiario);
            this.kilosDiario = itemView.findViewById(R.id.kilosDiario);
            this.diferenciaPesoDiario = itemView.findViewById(R.id.diferenciaPesoDiario);
            this.comentariosDiario = itemView.findViewById(R.id.comentariosDiario);
        }


        public void bind(EntradaPeso entradaPeso, int diferenciasPeso){
                fechaEntradaPeso.setText(entradaPeso.getFecha());
                if (!Objects.isNull(entradaPeso.getImagen())){
                    diarioImagen.setVisibility(View.VISIBLE);
                    diarioImagen.setImageBitmap(entradaPeso.getImagen());
                }else {
                    diarioImagen.setVisibility(View.GONE);
                }
                grasasPorcentajeDiario.setText("[" + entradaPeso.getPorcentajeGrasa() + " %]");
                imcCalculadoDiario.setText("{ " + entradaPeso.getImc() + " }");
                kilosDiario.setText(entradaPeso.getPeso() + " kg");
                if (diferenciasPeso > 0){
                    diferenciaPesoDiario.setText("+" + diferenciasPeso + " kg");
                    diferenciaPesoDiario.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.buttons_color_rosa));
                }else {
                    if (diferenciasPeso < 0){
                        diferenciaPesoDiario.setText("-" + diferenciasPeso + " kg");
                        diferenciaPesoDiario.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.buttons_color_verde));
                    }else {
                        diferenciaPesoDiario.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.gris_texto));
                    }
                }

                if (entradaPeso.getComentario() == null || entradaPeso.getComentario().equals("")){
                    comentariosDiario.setVisibility(View.GONE);
                }else {
                    comentariosDiario.setVisibility(View.VISIBLE);
                    comentariosDiario.setText(entradaPeso.getComentario());
                }
        }
    }
}

