package com.example.tbro402mobileapplication.DB.DBClass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term ter);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Term> ter);

    @Delete
    void deleteTerm(Term ter);

    @Query("SELECT * FROM Term WHERE id = :id")
    Term getTermById(int id);

    @Query("SELECT * FROM Term")
    LiveData<List<Term>> getAll();

    @Query("DELETE FROM Term")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM Term")
    int getCount();
}
