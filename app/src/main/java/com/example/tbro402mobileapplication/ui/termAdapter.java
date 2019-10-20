package com.example.tbro402mobileapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.R;

import java.util.List;

public class termAdapter extends ScrollView {

    private final List<Term> terms;
    private final Context context;

    public termAdapter(List<Term> terms, Context context) {
        super(context);
        this.terms = terms;
        this.context = context;
    }

}
