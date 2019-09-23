package com.formato.isp.GestionDocumental;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.formato.isp.MenuLateral.EncuestasRealizadas.EncuestasViewModel;
import com.formato.isp.R;
import com.formato.isp.utils.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleGestionDocumental extends Fragment {

    View root;
    public  TextView nombre, correo;
    CircularImageView foto;
    public DetalleGestionDocumental() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detalle_gestion_documental, container, false);
        nombre = root.findViewById(R.id.IdNombre);
        correo = root.findViewById(R.id.IdInformacion);
        foto = root.findViewById(R.id.IdImagen);

        //final TextView textView =  info.setText("TEXTO"); info.setText("TEXTO");root.findViewById(R.id.nombre);

        if (getArguments() != null) {
            String n = getArguments().getString("nombre");
            String c = getArguments().getString("email");
            int f = getArguments().getInt("foto");

            nombre.setText(n);
            correo.setText(c);
            Tools.displayImageRound(root.getContext(), foto ,f);
        }
        return root;
    }

}
