package com.formato.isp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formato.isp.R;
import com.formato.isp.model.Revision;
import com.formato.isp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasicDetalleGestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Revision> itemsdetemp = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Revision obj, int position);
    }

    public AdapterListBasicDetalleGestion(Context context, List<Revision> items) {
        this.itemsdetemp = items;
        ctx = context;
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView revi_desc;
        public TextView revi_fechainicio;
        public TextView revi_fechafinal;
        public View lyt_parent;
        public ProgressBar progreso;

        public OriginalViewHolder(View v) {
            super(v);
           image = (ImageView) v.findViewById(R.id.image);
           revi_desc = (TextView) v.findViewById(R.id.id_desc);
           revi_fechainicio= (TextView) v.findViewById(R.id.revi_fechainicio);
           revi_fechafinal= (TextView) v.findViewById(R.id.revi_fechafinal);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent_detalle_gest);
            progreso = v.findViewById(R.id.idprogreso);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_detalle_gestion, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Revision p = itemsdetemp.get(position);
            view.revi_desc.setText(p.getRevi_id());
            view.revi_fechainicio.setText( p.getRevi_fechainicio());
            view.revi_fechafinal.setText(p.getRevi_fechafinal());
            view.progreso.setProgress(50);

        }
    }

    @Override
    public int getItemCount() {
        return itemsdetemp.size();
    }
}
