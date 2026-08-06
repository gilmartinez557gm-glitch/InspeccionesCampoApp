package com.ucenm.inspeccionescampoapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ucenm.inspeccionescampoapp.R;
import com.ucenm.inspeccionescampoapp.models.Inspeccion;
import java.util.List;
import android.content.Intent;
import com.ucenm.inspeccionescampoapp.activities.DetalleInspeccionActivity;


public class InspeccionAdapter extends RecyclerView.Adapter<InspeccionAdapter.ViewHolder> {

    private List<Inspeccion> lista;

    public InspeccionAdapter(List<Inspeccion> lista){

        this.lista = lista;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_inspeccion,
                        parent,
                        false
                );


        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {


        Inspeccion inspeccion = lista.get(position);


        holder.tvTitulo.setText(
                inspeccion.getTitulo()
        );


        holder.tvDescripcion.setText(
                inspeccion.getDescripcion()
        );


        holder.tvEstado.setText(
                "Estado: " + inspeccion.getEstado()
        );


        holder.tvFecha.setText(
                "Fecha: " + inspeccion.getFechaInspeccion()
        );
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    DetalleInspeccionActivity.class
            );


            intent.putExtra(
                    "Id",
                    inspeccion.getId()
            );


            intent.putExtra(
                    "Titulo",
                    inspeccion.getTitulo()
            );


            intent.putExtra(
                    "Descripcion",
                    inspeccion.getDescripcion()
            );


            intent.putExtra(
                    "Estado",
                    inspeccion.getEstado()
            );


            v.getContext().startActivity(intent);

        });

    }



    @Override
    public int getItemCount() {

        return lista.size();

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitulo;
        TextView tvDescripcion;
        TextView tvEstado;
        TextView tvFecha;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);


            tvTitulo =
                    itemView.findViewById(R.id.tvTitulo);

            tvDescripcion =
                    itemView.findViewById(R.id.tvDescripcion);

            tvEstado =
                    itemView.findViewById(R.id.tvEstado);

            tvFecha =
                    itemView.findViewById(R.id.tvFecha);


        }
    }

}