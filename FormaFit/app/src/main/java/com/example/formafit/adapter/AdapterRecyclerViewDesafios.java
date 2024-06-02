package com.example.formafit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formafit.R;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.example.formafit.fragments.BasculaFragment;
import com.example.formafit.fragments.CrearDesafioFragment;
import com.example.formafit.java.Desafio;
import com.example.formafit.java.EntradaPeso;

import java.util.LinkedList;
import java.util.Objects;

public class AdapterRecyclerViewDesafios extends RecyclerView.Adapter<AdapterRecyclerViewDesafios.DesafiosViewHolder> {

    private LinkedList<Desafio> desafios;

    public AdapterRecyclerViewDesafios(LinkedList<Desafio> desafioRecycler){
        this.desafios = desafioRecycler;
    }

    @NonNull
    @Override
    public AdapterRecyclerViewDesafios.DesafiosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_desafios, parent, false);
        return new AdapterRecyclerViewDesafios.DesafiosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesafiosViewHolder holder, int position) {
        Desafio itemActual = desafios.get(position);
        holder.bind(itemActual);

        holder.checkboxDesafios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDatosHelper dbHelper = new BaseDatosHelper(v.getContext());
                int newCheckedState = (itemActual.getIs_checked() == 0) ? 1 : 0;
                dbHelper.upgradeCambiarDesafiosIs_Checked(itemActual.getId(), newCheckedState);

                // Obtener la posición actual del elemento
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    desafios.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, desafios.size()); // Notificar cambios en la lista
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return desafios.size();
    }

    class DesafiosViewHolder extends RecyclerView.ViewHolder{

        private TextView tituloDesafios, descripcionDesafios;
        private ImageView imagenDesafios;
        private CheckBox checkboxDesafios;

        public DesafiosViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tituloDesafios = itemView.findViewById(R.id.tituloDesafios);
            this.descripcionDesafios = itemView.findViewById(R.id.descripcionDesafios);
            this.checkboxDesafios = itemView.findViewById(R.id.checkboxDesafios);
            this.imagenDesafios = itemView.findViewById(R.id.imagenDesafios);
        }

        public void bind(Desafio desafio){
            // Limpiar las vistas antes de configurarlas
            imagenDesafios.setImageDrawable(null);
            tituloDesafios.setText("");
            descripcionDesafios.setText("");
            checkboxDesafios.setChecked(false);

            // Configurar las vistas con los datos del desafio actual
            if (desafio.getImg() != null){
                imagenDesafios.setImageBitmap(desafio.getImg());
            }else {
                imagenDesafios.setImageResource(R.drawable.logo_formafit);
            }
            tituloDesafios.setText(desafio.getTitulo());
            descripcionDesafios.setText(desafio.getDescripcion());
            checkboxDesafios.setChecked(desafio.getIs_checked() == 1);
        }
    }
}
