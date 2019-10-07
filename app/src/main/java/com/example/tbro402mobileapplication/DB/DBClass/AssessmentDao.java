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
    void insertAssessment(assessment assess);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(List<assessment> assess);

    @Delete
    void deleteAssessment(assessment assess);

    @Query("SELECT * FROM assessment WHERE id = :id")
    assessment getAssessmentById(int id);

    @Query("SELECT * FROM assessment ORDER BY course")
    LiveData<List<assessment>> getAll();

    @Query("DELETE FROM assessment")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM assessment")
    int getCount();
}
