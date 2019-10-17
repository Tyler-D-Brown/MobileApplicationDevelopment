package com.example.tbro402mobileapplication.DB.DBClass;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tbro402mobileapplication.Utilities.SampleData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<Assessment>> assessments;
    public LiveData<List<Course>> courses;
    public LiveData<List<Mentor>> mentors;
    public LiveData<List<Term>> terms;
    private AppDatabase db;
    private Executor execute = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        db = AppDatabase.getInstance(context);
        terms = getAllTerms();
    }

    public void add_term(final Term term){
        execute.execute(new Runnable() {
            @Override
            public void run() {
                db.termDao().insertTerm(term);
            }
        });
    }

    public void delete_term(final Term term){
        execute.execute(new Runnable() {
            @Override
            public void run() {
                db.termDao().deleteTerm(term);
            }
        });
    }

    private LiveData<List<Term>> getAllTerms(){
        return db.termDao().getAll();
    }

    private LiveData<List<Course>> getAllCourses(){ return db.courseDao().getAll(); }

    private LiveData<List<Course>> getTermCourses(int id){ return db.courseDao().getCourseByTerm(id); }

    private LiveData<List<Assessment>> getAllAssessments(){ return db.assessmentDao().getAll(); }

    private LiveData<List<Assessment>> getCourseAssessments(int id){ return db.assessmentDao().getCourseAssessment(id); }
}
