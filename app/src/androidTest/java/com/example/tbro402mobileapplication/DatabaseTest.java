package com.example.tbro402mobileapplication;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.tbro402mobileapplication.DB.DBClass.AppDatabase;
import com.example.tbro402mobileapplication.DB.DBClass.TermDao;
import com.example.tbro402mobileapplication.Utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase db;
    private TermDao termDao;

    @Before
    public void createDB(){
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        termDao = db.termDao();
        Log.i(TAG, "database Created");

    }

    @After
    public void closeDB(){
        db.close();
        Log.i(TAG, "database Closed");
    }

    @Test
    public void createAndRetrieveTerms(){
        termDao.insertAll(SampleData.getTerms());
        int count = termDao.getCount();
        Log.i(TAG, "create terms Size = " + count);
        assertEquals(SampleData.getTerms().size(), count);
    }
}
