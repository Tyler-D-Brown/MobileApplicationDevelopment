package com.example.tbro402mobileapplication.DB;

import android.provider.BaseColumns;

public final class dbCourse {
    private dbCourse(){}

    public static class CourseEntry implements BaseColumns {
        public static final String TABLE_NAME = "course";
        //public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String START = "startDate";
        public static final String END  = "endDate";
        public static final String TERM = "term";
        public static final String STATUS = "status";
        public static final String NOTE = "note";
        public static final String MENTOR = "mentor";
    }
}