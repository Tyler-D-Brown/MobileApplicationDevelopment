package com.example.tbro402mobileapplication.DB.DBClass;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assess);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(List<Assessment> assess);

    @Delete
    void deleteAssessment(Assessment assess);

    @Query("SELECT * FROM Assessment WHERE id = :id")
    Assessment getAssessmentById(int id);

    @Query("SELECT * FROM Assessment ORDER BY course")
    LiveData<List<Assessment>> getAll();

    @Query("SELECT * FROM Assessment WHERE id = :id")
    LiveData<List<Assessment>> getCourseAssessment(int id);

    @Query("DELETE FROM Assessment")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Assessment")
    int getCount();
}
