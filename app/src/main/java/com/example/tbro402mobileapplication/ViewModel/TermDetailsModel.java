package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermDetailsModel extends AndroidViewModel {

    public MutableLiveData<Term> liveTerm= new MutableLiveData<>();
    private AppRepository termRepository;
    public LiveData<List<Course>> courses;
    public LiveData<List<Course>> termCourses;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermDetailsModel(@NonNull Application application) {
        super(application);

        termRepository = AppRepository.getInstance(getApplication());
        courses = termRepository.courses;
    }

    public void loadData(final int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Term term = termRepository.getTermById(termId);
                liveTerm.postValue(term);
            }
        });
    }
}
