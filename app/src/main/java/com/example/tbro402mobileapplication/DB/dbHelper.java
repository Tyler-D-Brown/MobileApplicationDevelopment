package com.example.tbro402mobileapplication.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.tbro402mobileapplication.DB.dbAssessment.AssessmentEntry;
import com.example.tbro402mobileapplication.DB.dbCourse.CourseEntry;
import com.example.tbro402mobileapplication.DB.dbMentor.MentorEntry;
import com.example.tbro402mobileapplication.DB.dbTerm.TermEntry;



public class dbHelper extends SQLiteOpenHelper {

    private static final String SQLCreateAssessmentTable =
            "CREATE TABLE " +  AssessmentEntry.TABLE_NAME + " (" +
                    AssessmentEntry._ID + " INTEGER PRIMARY KEY," +
                    AssessmentEntry.COURSE + " INTEGER," +
                    AssessmentEntry.TITLE + " TEXT," +
                    AssessmentEntry.START + " TEXT," +
                    AssessmentEntry.END + " TEXT," +
                    AssessmentEntry.TYPE + " TEXT," +
                    AssessmentEntry.STATUS + " TEXT)";

    private static final String SQLCreateCourseTable =
            "CREATE TABLE " +  CourseEntry.TABLE_NAME + " (" +
                    CourseEntry._ID + " INTEGER PRIMARY KEY," +
                    CourseEntry.TERM + " INTEGER," +
                    CourseEntry.MENTOR + " INTEGER," +
                    CourseEntry.TITLE + " TEXT," +
                    CourseEntry.START + " TEXT," +
                    CourseEntry.END + " TEXT," +
                    CourseEntry.NOTE + " TEXT," +
                    CourseEntry.STATUS + " TEXT)";

    private static final String SQLCreateMentorTable =
            "CREATE TABLE " + MentorEntry.TABLE_NAME + " (" +
                    MentorEntry._ID + " INTEGER PRIMARY KEY," +
                    MentorEntry.NAME + " TEXT," +
                    MentorEntry.EMAIL + " TEXT," +
                    MentorEntry.PHONE + " TEXT)";

    private static final String SQLCreateTermTable =
            "CREATE TABLE " + TermEntry.TABLE_NAME + " (" +
                    TermEntry._ID + " INTEGER PRIMARY KEY," +
                    TermEntry.TITLE + " TEXT," +
                    TermEntry.START + " TEXT," +
                    TermEntry.END + " TEXT)";

    private static final String SQLDeleteAssessmentTable =
            "DROP TABLE IF EXISTS " + AssessmentEntry.TABLE_NAME;

    private static final String SQLDeleteTermTable =
            "DROP TABLE IF EXISTS " + TermEntry.TABLE_NAME;

    private static final String SQLDeleteMentorTable =
            "DROP TABLE IF EXISTS " + MentorEntry.TABLE_NAME;

    private static final String SQLDeleteCourseTable =
            "DROP TABLE IF EXISTS " + CourseEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WGUMobileAppDev.db";

    public dbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQLCreateAssessmentTable);
        db.execSQL(SQLCreateCourseTable);
        db.execSQL(SQLCreateMentorTable);
        db.execSQL(SQLCreateTermTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLDeleteAssessmentTable);
        db.execSQL(SQLDeleteTermTable);
        db.execSQL(SQLDeleteMentorTable);
        db.execSQL(SQLDeleteCourseTable);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
