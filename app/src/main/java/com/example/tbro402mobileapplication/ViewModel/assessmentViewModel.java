package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Assessment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class assessmentViewModel extends AndroidViewModel {
    public MutableLiveData<Assessment> liveAssessment = new MutableLiveData<>();
    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public assessmentViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int assessment) {
        executor.execute(
            new Runnable() {
                @Override
                public void run() {
                    if(repository.getassessmentById(assessment)!=null){
                        try{
                            liveAssessment.postValue(repository.getassessmentById(assessment));
                        }
                        catch (Exception e){
                            Log.e(TAG, "Exception" + e);
                        }
                    }
                }
            }
        );
    }

    public void saveAssessment(Assessment assess) {
        Log.i(TAG, "id: " + assess.getId());
        Log.i(TAG, "Course: " + assess.getCourse());
        Log.i(TAG, "title:" + assess.getTitle());
        Log.i(TAG, "type: " + assess.getType());
        Log.i(TAG, "start: "+ assess.getStartDate());
        Log.i(TAG, "end: " + assess.getEndDate());
        Log.i(TAG, "Status: " + assess.getStatus());

        if(assess.getId()==-1){
            Assessment assessment = new Assessment(assess.getStartDate(), assess.getEndDate(),
                    assess.getTitle(), assess.getCourse(), assess.getType(), assess.getStatus());
            try {
                Log.i(TAG, "id: " + assessment.getId());
            }catch(Exception e){
                Log.i(TAG,"ID null");
            }
            Log.i(TAG, "Course: " + assessment.getCourse());
            Log.i(TAG, "title:" + assessment.getTitle());
            Log.i(TAG, "type: " + assessment.getType());
            Log.i(TAG, "start: "+ assessment.getStartDate());
            Log.i(TAG, "end: " + assessment.getEndDate());
            Log.i(TAG, "Status: " + assessment.getStatus());
            repository.insertAssessment(assessment);
        }
        else {
            repository.insertAssessment(assess);
        }
    }
}
