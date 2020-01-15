package com.example.tbro402mobileapplication.ViewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tbro402mobileapplication.DB.DBClass.AppRepository;
import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Mentor;
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
    public Mentor mentor;


    public courseModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
        assessments = repository.assessments;
        mentor = new Mentor();
    }

    public void loadData(final int ID) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(repository.getCourseById(ID)!=null) {
                    try {
                        liveCourse.postValue(new Course(repository.getCourseById(ID)));
                        courseAssessments = repository.getCourseAssessments(ID);
                        if(courseAssessments != null) {
                            for (int i = 0; i < courseAssessments.getValue().size(); i++) {
                                Log.e(TAG, "assessment Title: " + courseAssessments.getValue().get(i).getTitle());
                            }
                        }else{
                            Log.e(TAG, "no assessments found");
                        }
                    } catch (Exception exception) {
                        Log.e(TAG, "Exception: " + exception);
                    }
                }else{
                    Log.e(TAG, "No course found");
                }
            }
        });
    }

    public void loadMentor(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "course ID " + liveCourse.getValue().getId() + " Mentor ID " + liveCourse.getValue().getMentor());
                if (liveCourse.getValue().getMentor() != -1) {
                    mentor = new Mentor(repository.getMentorByID(liveCourse.getValue().getMentor()));
                }
            }
        });
    }

    public void saveCourse(Course course) {
        Log.i(TAG, "term details: ");
        Log.i(TAG, "term Title: " + course.getTitle());
        Log.i(TAG, "term Start: " + course.getStartDate());
        Log.i(TAG, "term End: " + course.getEndDate());
        Log.i(TAG, "term ID: " + course.getId());
        if(course.getId() == -1){
            course = new Course(course.getTitle(), course.getStartDate(), course.getEndDate(),
                    course.getTerm(), course.getStatus(), course.getNote(), course.getMentor());
            repository.insertCourse(course);
        } else {
            repository.insertCourse(course);
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
