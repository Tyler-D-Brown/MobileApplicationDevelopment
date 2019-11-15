package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;
import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;

public class TermDetailsModel extends AndroidViewModel {

    public MutableLiveData<Term> liveTerm = new MutableLiveData<>();
    private AppRepository termRepository;
    public LiveData<List<Course>> courses;
    public LiveData<List<Course>> termCourses;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermDetailsModel(@NonNull Application application) {
        super(application);
        termRepository = AppRepository.getInstance(getApplication());
        courses = termRepository.courses;
    }

    /*public Term getTerm(final int termId){
        Log.i(TAG, "intent received pre run" + termId);
        LiveData<Term> term;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                term. = new Term(termRepository.getTermById(termId));
                return(term);
            }
        });
        return(term);
    }*/

    public void loadData(final int termId) {
        Log.i(TAG, "intent received pre run" + termId);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Term term = new Term(termRepository.getTermById(termId));
                //liveTerm = new Term(termRepository.getTermById(termId));
                //Term term = new Term(-1, "testing", new Date(), new Date());
                try {
                    Log.i(TAG, "pre post value");
                    liveTerm.postValue(term);
                    Log.i(TAG, "Post post value");
                    Log.i(TAG, "intent received" + termId);
                    Log.i(TAG, "termID during load data: " + term.getId());
                    Log.i(TAG, "liveTermID during load data: " + liveTerm.getValue().getId());
                }catch (Exception exception){
                    Log.i(TAG, "Exception: " + exception);
                }
                //Log.i(TAG, "termID during load data: " + liveTerm.getValue().getId());
                termCourses = termRepository.getTermCourses(termId);
            }
        });
    }

    public void saveTerm(Term term) {
        Log.i(TAG, "term details: ");
        Log.i(TAG, "term Title: " + term.getTitle());
        Log.i(TAG, "term Start: " + term.getStartDate());
        Log.i(TAG, "term End: " + term.getEndDate());
        Log.i(TAG, "term ID: " + term.getId());
        if(term.getId() == -1){
            term = new Term(term.getTitle(),
                    term.getStartDate(), term.getEndDate());
            termRepository.insertTerm(term);
        } else {
            termRepository.insertTerm(term);
        }
    }
}
