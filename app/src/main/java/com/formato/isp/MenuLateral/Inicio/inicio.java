package com.formato.isp.MenuLateral.Inicio;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.formato.isp.GestionFundacion.Sesion;
import com.formato.isp.R;
import com.formato.isp.DesarrolloEncuesta.opcionPrincipal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class inicio extends Fragment {

    FloatingActionButton btnInicio, btnInforme, btnGestion;
    private CircularImageView logo;
    private TextView nombre;
    private TextView ubicacion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        logo = (CircularImageView)root.findViewById(R.id.imageLogoFun);
        nombre = (TextView)root.findViewById(R.id.nom_fundacion);
        ubicacion = (TextView)root.findViewById(R.id.ubi_fundacion);

        Sesion session;
        session = new Sesion(root.getContext());

        String ruta = session.getLogo();
        Picasso.with(root.getContext()).load(ruta).into(logo);
        nombre.setText(session.getNombreFun());
        ubicacion.setText(session.getDireccion());

        btnInicio = root.findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrir = new Intent(view.getContext(), opcionPrincipal.class);
                startActivity(abrir);
            }
        });

        btnGestion = root.findViewById(R.id.btnGestion);
        btnGestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestion_documentalnavigation);//Abre el fragmento
            }
        });

        return root;
    }

}
