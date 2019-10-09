package com.example.tbro402mobileapplication.DB.DBClass;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Assessment.class, Course.class, Mentor.class, Term.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "C196.db";
    private static volatile AppDatabase instance;
    private static final Object Lock = new Object();

    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract MentorDao mentorDao();
    public abstract TermDao termDao();

    public static AppDatabase getInstance(Context context){
        if (instance == null){
            synchronized (Lock){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
