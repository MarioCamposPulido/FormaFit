package com.example.formafit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formafit.R;
import com.example.formafit.java.Desafio;
import com.example.formafit.java.EntradaPeso;

import java.util.LinkedList;
import java.util.Objects;

public class AdapterRecyclerViewDesafios extends RecyclerView.Adapter<AdapterRecyclerViewDesafios.DesafiosViewHolder> {

    private LinkedList<Desafio> desafios;

    public AdapterRecyclerViewDesafios(LinkedList<Desafio> desafioRecycler){
        this.desafios = desafioRecycler;
    };


    @NonNull
    @Override
    public AdapterRecyclerViewDesafios.DesafiosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_desafios, parent, false);
        return new AdapterRecyclerViewDesafios.DesafiosViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DesafiosViewHolder holder, int position) {
        holder.bind(desafios.get(position));
    }

    /**
     * Devuelve el número total de elementos de la lista
     * @return
     */
    @Override
    public int getItemCount() {
        return desafios.size();
    }

    /**
     * Clase para referirse a la celda personalizada del recycler
     */
    class DesafiosViewHolder extends RecyclerView.ViewHolder{

        private TextView tituloDesafios, descripcionDesafios, checkboxDesafios;
        private ImageView imagenDesafios;


        /**
         * Declaramos lo que es cada item, para interactuar con él
         * @param itemView
         */
        public DesafiosViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tituloDesafios = itemView.findViewById(R.id.tituloDesafios);
            this.descripcionDesafios = itemView.findViewById(R.id.descripcionDesafios);
            this.checkboxDesafios = itemView.findViewById(R.id.checkboxDesafios);
            this.imagenDesafios = itemView.findViewById(R.id.imagenDesafios);
        }


        public void bind(Desafio desafio){
            if (!Objects.isNull(desafio.getImg())){
                imagenDesafios.setImageBitmap(desafio.getImg());
            }
            tituloDesafios.setText(desafio.getTitulo());
            descripcionDesafios.setText(desafio.getDescripcion());
        }
    }
}
