package com.formato.isp.Adaptadores;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formato.isp.Clases.Pregunta;
import com.formato.isp.R;

import org.w3c.dom.Text;

import java.util.List;
import com.formato.isp.Clases.Pregunta;

public class adaptadorPregunta extends RecyclerView.Adapter<adaptadorPregunta.ViewHolder>{

    private List<Pregunta> listItems;
    private Context context;

    public adaptadorPregunta(List<Pregunta> listItem, Context context) {
        this.listItems = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pregunta, parent, false);
         return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pregunta listItem = listItems.get(position);
        holder.numero.setText(listItem.getDescripcion());
        holder.contenido.setText(listItem.getContenido());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView numero;
        public TextView contenido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numero = (TextView) itemView.findViewById(R.id.numero);
            //contenido = (TextView) itemView.findViewById(R.id.contenido);
        }
    }
}
