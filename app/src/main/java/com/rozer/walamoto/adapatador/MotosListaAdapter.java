package com.rozer.walamoto.adapatador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rozer.walamoto.R;
import com.rozer.walamoto.entidades.Motos;

import java.util.List;

public class MotosListaAdapter extends RecyclerView.Adapter<MotosListaAdapter.ViewHolder> {

    List<Motos> listamotos;

    private final OnEventClickListener listener;

    public interface OnEventClickListener{
        void onItemClick (Motos motos);
    }

    public MotosListaAdapter(List<Motos> listamotos, OnEventClickListener listener) {
        this.listamotos = listamotos;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_motos,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Motos motos = listamotos.get(position);
        if (motos.isVendido()){
            holder.img.setImageResource(R.drawable.vendido_sold);
        }else holder.img.setImageResource(R.drawable.moto_disponible);
        //holder.img.setImageResource(motos.isVendido() ? R.drawable.moto_disponible:R.drawable.vendido_sold);
        holder.marca.setText(motos.getMarca());
        holder.modelo.setText(motos.getModelo());
        holder.km.setText(motos.getKm() + " km");
        holder.year.setText(String.valueOf(motos.getYear()));
        holder.precio.setText(motos.getPrecio() + "€");

        holder.eventoContenedor(listamotos.get(position),listener);

        if (position %2 ==  1){
            holder.contenedor.setBackgroundColor(holder.azulClaro);
        }else{
            holder.contenedor.setBackgroundColor(holder.azulMedio);
        }

    }

    @Override
    public int getItemCount() {
        return this.listamotos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ImageView img;
        TextView marca,modelo,km,year,precio;
        LinearLayout contenedor;
        int azulClaro,azulMedio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.img_itemlist);
            marca = itemView.findViewById(R.id.tvmarca_itemlist);
            modelo = itemView.findViewById(R.id.tvmodelo_itemlist);
            km = itemView.findViewById(R.id.tvkm_itemlist);
            year = itemView.findViewById(R.id.tvanio_itemlist);
            precio = itemView.findViewById(R.id.tvprecio_itemlist);
            contenedor= itemView.findViewById(R.id.contenedor_itemMoto);
            //Asignamos colores a las filas pares e impares
            azulClaro = itemView.getResources().getColor(R.color.azulClaro);
            azulMedio = itemView.getResources().getColor(R.color.azulMedio);

        }
        public void eventoContenedor(Motos motos, OnEventClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(motos));
        }

    }


}
