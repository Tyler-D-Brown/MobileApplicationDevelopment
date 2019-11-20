package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class courseModel extends AndroidViewModel {

    public MutableLiveData<Course> liveCourse = new MutableLiveData<>();
    private AppRepository repository;
    public LiveData<List<Assessment>> assessments;
    public LiveData<List<Assessment>> courseAssessments;
    private Executor executor = Executors.newSingleThreadExecutor();


    public courseModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
        assessments = repository.assessments;
    }

    public void loadData(final int ID) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(repository.getTermById(ID)!=null) {
                    try {
                        liveCourse.postValue(new Course(repository.getCourseById(ID)));
                    } catch (Exception exception) {
                        Log.i(TAG, "Exception: " + exception);
                    }
                }
                courseAssessments = repository.getCourseAssessments(ID);
            }
        });
    }

    public void saveCourse(Term term) {
        Log.i(TAG, "term details: ");
        Log.i(TAG, "term Title: " + term.getTitle());
        Log.i(TAG, "term Start: " + term.getStartDate());
        Log.i(TAG, "term End: " + term.getEndDate());
        Log.i(TAG, "term ID: " + term.getId());
        if(term.getId() == -1){
            term = new Term(term.getTitle(),
                    term.getStartDate(), term.getEndDate());
            repository.insertTerm(term);
        } else {
            repository.insertTerm(term);
        }
    }


    public void deleteAssessment(final int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.deleteAssessment(repository.getassessmentById(id));
            }
        });
    }
}
