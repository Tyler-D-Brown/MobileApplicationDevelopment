package com.example.tbro402mobileapplication.DB.DBClass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course cor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(List<Course> cor);

    @Delete
    void deleteCourse(Course cor);

    @Query("SELECT * FROM Course WHERE id = :id")
    Course getCourseById(int id);

    @Query("SELECT * FROM Course ORDER BY term")
    LiveData<List<Course>> getAll();

    @Query("DELETE FROM Course")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Course")
    int getCount();
}
