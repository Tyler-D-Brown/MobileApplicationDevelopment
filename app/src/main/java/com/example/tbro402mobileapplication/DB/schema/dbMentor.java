package com.example.tbro402mobileapplication.DB;

import android.provider.BaseColumns;

public final class dbMentor {
    private dbMentor(){}

    public static class MentorEntry implements BaseColumns {
        public static final String TABLE_NAME = "Mentor";
        //public static final String ID = "ID";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
    }
}
