package com.formato.isp.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formato.isp.R;
import com.formato.isp.model.Empresa;
import com.formato.isp.model.People;
import com.formato.isp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<People> items = new ArrayList<>();
    private List<Empresa> itemsemp = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Empresa obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasic(Context context, List<Empresa> items) {
        this.itemsemp = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView barrio;
        public TextView telefono;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            barrio = (TextView) v.findViewById(R.id.barrio);
            telefono = (TextView) v.findViewById(R.id.Idtelefono);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_gestion, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Empresa p = itemsemp.get(position);
            view.name.setText(p.empr_nombre);
            view.barrio.setText(p.empr_barrio);
            view.telefono.setText(p.empr_telefono);
            Tools.displayImageRound(ctx, view.image, p.empr_image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, itemsemp.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsemp.size();
    }

}