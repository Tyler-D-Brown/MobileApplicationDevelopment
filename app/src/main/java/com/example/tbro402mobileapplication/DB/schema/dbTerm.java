package com.example.tbro402mobileapplication.DB;

import android.provider.BaseColumns;

public final class dbTerm {
    private dbTerm(){}

    public static class TermEntry implements BaseColumns{
        public static final String TABLE_NAME = "term";
        //public static final String ID = "ID";
        public static final String TITLE = "title";
        public static final String START = "startDate";
        public static final String END = "endDate";
    }


}


