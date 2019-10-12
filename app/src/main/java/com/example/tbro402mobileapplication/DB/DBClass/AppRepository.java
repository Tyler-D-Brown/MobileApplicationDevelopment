package com.example.tbro402mobileapplication.DB.DBClass;

import com.example.tbro402mobileapplication.Utilities.SampleData;

import java.util.Date;
import java.util.List;

public class AppRepository {
    private static final AppRepository ourInstance = new AppRepository();

    public List<Assessment> assessments;
    public List<Course> courses;
    public List<Mentor> mentors;
    public List<Term> terms;

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
        terms = SampleData.getTerms();
    }
}
