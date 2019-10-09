package com.example.tbro402mobileapplication.DB;

import android.provider.BaseColumns;

public final class dbAssessment {
    private dbAssessment(){}

    public static class AssessmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Assessment";
        public static final String TITLE = "title";
        public static final String START = "startDate";
        public static final String END  = "endDate";
        public static final String COURSE = "Course";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
    }
}