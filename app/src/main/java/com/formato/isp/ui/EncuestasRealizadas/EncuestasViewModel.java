package com.formato.isp.ui.EncuestasRealizadas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EncuestasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EncuestasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ver encuestas realizadas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}