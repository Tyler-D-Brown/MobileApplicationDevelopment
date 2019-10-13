package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppDatabase;
import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Mentor;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public LiveData<List<Assessment>> assessments;
    public LiveData<List<Course>> courses;
    public LiveData<List<Mentor>> mentors;
    public LiveData<List<Term>> terms;
    private AppRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        terms = repository.terms;
    }
}
