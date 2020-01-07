package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Assessment;

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
        if(assess.getId()==-1){
            Assessment assessment = new Assessment(assess.getStartDate(), assess.getEndDate(),
                    assess.getTitle(), assess.getCourse(), assess.getType(), assess.getStatus());
            repository.insertAssessment(assessment);
        }
        else {
            repository.insertAssessment(assess);
        }
    }
}
