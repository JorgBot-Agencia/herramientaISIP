package com.formato.isp;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.formato.isp.ui.home.HomeViewModel;
import com.formato.isp.utils.Tools;


/**
 * A simple {@link Fragment} subclass.
 */
public class opcionesPrincipales extends Fragment {

    Button btnConsultar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_opciones_principales, container, false);

        btnConsultar= root.findViewById(R.id.btn_next);
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
