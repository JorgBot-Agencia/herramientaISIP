package com.formato.isp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.formato.isp.Gestion_documental;
import com.formato.isp.R;
import com.formato.isp.menuprincipal;
import com.formato.isp.informacionEmpresarial;
import com.formato.isp.opcionPrincipal;

public class HomeFragment extends Fragment {
    View vista;
    Button btnFormatoIsp;
    Button btnGestionDocumental;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        btnFormatoIsp = root.findViewById(R.id.formato_isp);
        btnGestionDocumental = root.findViewById(R.id.btnGestionDocumental);
        btnGestionDocumental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestion_documentalnavigation);//Abre el fragmento
            }
        });

        btnFormatoIsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), opcionPrincipal.class);
                startActivity(intent);
            }
        });
        return root;
    }
}