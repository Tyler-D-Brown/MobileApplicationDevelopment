package com.example.tbro402mobileapplication.DB.DBClass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MentorDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(Mentor ment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(List<Mentor> ment);

    @Delete
    void deleteMentor(Mentor ment);

    @Query("SELECT * FROM Mentor WHERE id = :id")
    Mentor getMentorById(int id);

    @Query("SELECT * FROM Mentor")
    LiveData<List<Mentor>> getAll();

    @Query("DELETE FROM Mentor")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Mentor")
    int getCount();
}
