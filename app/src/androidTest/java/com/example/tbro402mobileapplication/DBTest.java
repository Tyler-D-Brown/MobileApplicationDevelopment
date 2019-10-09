package com.example.tbro402mobileapplication;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.tbro402mobileapplication.DB.DBClass.AppDatabase;
import com.example.tbro402mobileapplication.DB.DBClass.AssessmentDao;
import com.example.tbro402mobileapplication.DB.DBClass.CourseDao;
import com.example.tbro402mobileapplication.DB.DBClass.MentorDao;
import com.example.tbro402mobileapplication.DB.DBClass.TermDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith((AndroidJUnit4.class))
public class DBTest {
    public static final String Tag = "Junit";
    private AppDatabase mdb;
    private AssessmentDao assess;
    private CourseDao course;
    private MentorDao ment;
    private TermDao term;

    @Before
    public void createDb(){
        Context cont = InstrumentationRegistry.getTargetContext();
        mdb = Room.inMemoryDatabaseBuilder(cont,
                AppDatabase.class).build();
        assess = mdb.assessmentDao();
        course = mdb.courseDao();
        ment = mdb.mentorDao();
        term = mdb.termDao();
        Log.i(Tag, "createDb");
    }

    @After
    public void closeDb(){
        mdb.close();
        Log.i(Tag, 'closeDb');
    }

    @Test
    public void createAndRetrieve(){
        assess.insertAssessment(Samp);
    }

}
