package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Mentor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class mentorViewModel extends AndroidViewModel {
    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();
    public MutableLiveData<Mentor> liveMentor = new MutableLiveData<>();


    public mentorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int mentor) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(repository.getMentorByID(mentor)!=null){
                    try{
                        liveMentor.postValue(repository.getMentorByID(mentor));
                    }
                    catch (Exception e){
                        Log.e(TAG, "Exception" + e);
                    }
                }
            }
        }
        );
    }

    public void saveMentor(Mentor ment) {
        if(ment.getId()==-1){
            Mentor mentor = new Mentor(ment.getName(),ment.getEmail(), ment.getPhone());
            repository.insertMentor(mentor);
        }
        else {
            repository.insertMentor(ment);
        }
    }
}
