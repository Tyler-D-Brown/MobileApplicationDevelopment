package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

public class TermDetailsModel extends AndroidViewModel {

    public MutableLiveData<Term> liveTerm = new MutableLiveData<>();
    private AppRepository repository;
    public LiveData<List<Course>> courses;
    public LiveData<List<Course>> termCourses;
    private Executor executor = Executors.newSingleThreadExecutor();
    private ExecutorService getterService = Executors.newSingleThreadExecutor();

    public TermDetailsModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
        courses = repository.courses;
    }

    /*public Term getTerm(final int termId){
        Log.i(TAG, "intent received pre run" + termId);
        LiveData<Term> term;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                term. = new Term(repository.getTermById(termId));
                return(term);
            }
        });
        return(term);
    }*/

    public void loadData(final int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(repository.getTermById(termId)!=null) {
                    try {
                        liveTerm.postValue(new Term(repository.getTermById(termId)));
                    } catch (Exception exception) {
                        Log.i(TAG, "Exception: " + exception);
                    }
                }
                termCourses = repository.getTermCourses(termId);
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
            repository.insertTerm(term);
        } else {
            repository.insertTerm(term);
        }
    }

    public void deleteCourse(final int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.deleteCourse(repository.getCourseById(id));
            }
        });
    }

    public int getAssess(final int id){
        Future<Integer> i = getterService.submit(new Callable<Integer>() {
            @Override
            public Integer call(){
                return repository.getAssessmentByTerm(id);
            }
        });
        try {
            Log.i("value", "assessment total" + i.get());
            return i.get();
        }catch(Exception e){
            Log.i("Error", e.toString());
        }
        return -1;
    }

    public int getCompletedAssess(final int id){
        Future<Integer> i = getterService.submit(new Callable<Integer>() {
            @Override
            public Integer call(){
                return repository.getCompleteAssessmentByTerm(id);
            }
        });
        try {
            Log.i("value", "Completed assessment total" + i.get());
            return i.get();
        }catch(Exception e){
            Log.i("Error", e.toString());
        }
        return -1;
    }
}
