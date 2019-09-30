package com.formato.isp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formato.isp.R;
import com.formato.isp.model.Revision;
import com.formato.isp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasicDetalleGestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Revision> itemsemp = new ArrayList<>();
    private Context ctx;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Revision obj, int position);
    }

    public AdapterListBasicDetalleGestion(Context context, List<Revision> items) {
        this.itemsemp = items;
        ctx = context;
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView revi_id;
        public TextView revi_fechainicio;
        public TextView revi_fechafinal;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
           image = (ImageView) v.findViewById(R.id.image);
           revi_id = (TextView) v.findViewById(R.id.id_revision);
           revi_fechainicio= (TextView) v.findViewById(R.id.revi_fechainicio);
           revi_fechafinal= (TextView) v.findViewById(R.id.revi_fechafinal);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent_detalle_gest);
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

            Revision p = itemsemp.get(position);
            view.revi_id.setText(p.getRevi_id());
            view.revi_fechainicio.setText( p.getRevi_fechainicio());
            view.revi_fechafinal.setText(p.getRevi_fechafinal());

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
