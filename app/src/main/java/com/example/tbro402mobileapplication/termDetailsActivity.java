package com.example.tbro402mobileapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tbro402mobileapplication.ViewModel.TermDetailsModel;

public class termDetailsActivity extends AppCompatActivity {
    private TermDetailsModel t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_details);
    }
}
