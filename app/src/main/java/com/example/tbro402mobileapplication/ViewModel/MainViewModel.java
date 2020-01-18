package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;

import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainViewModel extends AndroidViewModel {
    public LiveData<List<Term>> terms;
    public AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();
    public LiveData<List<Course>> courses;
    private ExecutorService getterService = Executors.newSingleThreadExecutor();

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        terms = repository.terms;
    }

    public void getCourses(int id){
        courses = repository.getTermCourses(id);

    }

    public void deleteTerm(final int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.delete_term(repository.getTermById(id));
            }
        });
    }

    public int getAssess(){
        Future<Integer> i = getterService.submit(new Callable<Integer>() {
            @Override
            public Integer call(){
                return repository.getAssessmentCount();
            }
        });
        try {
            return i.get();
        }catch(Exception e){
            Log.i("Error", e.toString());
        }
        return -1;
    }

    public int getCompletedAssess(){
        Future<Integer> i = getterService.submit(new Callable<Integer>() {
            @Override
            public Integer call(){
                return repository.getCompletedAssessments();
            }
        });
        try {
            return i.get();
        }catch(Exception e){
            Log.i("Error", e.toString());
        }
        return -1;
    }
}
