package com.formato.isp.ConsultarEmpresa;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formato.isp.R;

public class ConsultaEmpresaFragment extends Fragment {

    private ConsultaEmpresaViewModel mViewModel;

    public static ConsultaEmpresaFragment newInstance() {
        return new ConsultaEmpresaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.consulta_empresa_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConsultaEmpresaViewModel.class);
        // TODO: Use the ViewModel
    }

}
