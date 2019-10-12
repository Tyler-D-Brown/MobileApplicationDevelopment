package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Mentor;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public List<Assessment> assessments = new ArrayList<>();
    public List<Course> courses = new ArrayList<>();
    public List<Mentor> mentors = new ArrayList<>();
    public List<Term> terms = new ArrayList<>();
    private AppRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance();
        terms = repository.terms;
    }
}
