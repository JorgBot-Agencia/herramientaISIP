package com.formato.isp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.formato.isp.ui.home.HomeViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class opcionesPrincipales extends Fragment {

    Button btnConsultar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_opciones_principales, container, false);

        btnConsultar= root.findViewById(R.id.btnConsultarEmpresa);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.informacionPrincipal);//Abre el fragmento
                //destino desde un fragmento origen.
            }
        });
        return root;
    }

}
