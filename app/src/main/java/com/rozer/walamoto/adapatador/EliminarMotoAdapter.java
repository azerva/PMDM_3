package com.rozer.walamoto.adapatador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rozer.walamoto.R;
import com.rozer.walamoto.entidades.Motos;

import java.util.ArrayList;

public class EliminarMotoAdapter extends RecyclerView.Adapter<EliminarMotoAdapter.ViewHolder> {

    ArrayList<Motos> listamotos;

    private OnEventClickListener listener;

    public interface OnEventClickListener{
        void onItemClick (Motos motos);
    }

    public EliminarMotoAdapter(ArrayList<Motos> listamotos, OnEventClickListener listener) {
        this.listamotos = listamotos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_moto,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Motos motos = listamotos.get(position);
        holder.marca.setText(motos.getMarca());
        holder.modelo.setText(motos.getModelo());
        holder.km.setText(String.valueOf(motos.getKm())+" km");
        holder.year.setText(String.valueOf(motos.getYear()));

        if (position %2 ==  1){
            holder.contenedor.setBackgroundColor(holder.azulClaro);
        }else{
            holder.contenedor.setBackgroundColor(holder.azulMedio);
        }

        holder.delete.setOnClickListener(v -> listener.onItemClick(motos));
    }

    @Override
    public int getItemCount() {
        return this.listamotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete;
        TextView marca,modelo,km,year;
        LinearLayout contenedor;
        int azulClaro,azulMedio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.btn_delete_item);
            marca = itemView.findViewById(R.id.marca_delete);
            modelo = itemView.findViewById(R.id.modelo_delete);
            km = itemView.findViewById(R.id.km_delete);
            year = itemView.findViewById(R.id.year_delete);
            contenedor = itemView.findViewById(R.id.contenedorCocheEliminar);
            azulClaro = itemView.getResources().getColor(R.color.azulClaro);
            azulMedio = itemView.getResources().getColor(R.color.azulMedio);
        }
    }
}
