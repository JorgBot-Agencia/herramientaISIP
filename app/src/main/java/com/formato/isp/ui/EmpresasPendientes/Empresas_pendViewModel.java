package com.formato.isp.ui.EmpresasPendientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Empresas_pendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Empresas_pendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ver empresas pendientes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}