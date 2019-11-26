package com.formato.isp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.formato.isp.GestionDocumental.DetalleGestionDocumental;
import com.formato.isp.R;
import com.formato.isp.model.Revision;
import com.formato.isp.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        public TextView idrevi;
        public TextView revi_desc;
        public TextView revi_fechainicio;
        public TextView revi_fechafinal;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            idrevi=v.findViewById(R.id.idrev);
           image = (ImageView) v.findViewById(R.id.image);
           revi_desc = (TextView) v.findViewById(R.id.id_desc);
           revi_fechainicio= (TextView) v.findViewById(R.id.revi_fechainicio);
           revi_fechafinal= (TextView) v.findViewById(R.id.revi_fechafinal);
           lyt_parent = (View) v.findViewById(R.id.lyt_parent_detalle_gest);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_detalle_gestion, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Revision p = itemsdetemp.get(position);
            view.idrevi.setText(p.getRevi_id());
            view.revi_desc.setText(p.getRevi_descripcion());
            view.revi_fechainicio.setText( p.getRevi_fechainicio());
            view.revi_fechafinal.setText(p.getRevi_fechafinal());
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, itemsdetemp.get(position), position);
                    }
                }
            });
            //consultar_datos_rev();
        }
    }

    @Override

    public int getItemCount() {
        return itemsdetemp.size();
    }
}
