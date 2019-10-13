package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

public class TermDetailsModel extends AndroidViewModel {

    public MutableLiveData<Term> liveTerm= new MutableLiveData<>();
    private AppRepository termRepository;

    public TermDetailsModel(@NonNull Application application) {
        super(application);
        termRepository = AppRepository.getInstance(getApplication());

    }
}
