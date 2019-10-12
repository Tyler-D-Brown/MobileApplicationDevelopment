package com.example.tbro402mobileapplication.Utilities;

import com.example.tbro402mobileapplication.DB.DBClass.Term;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleData {

    private static Term t1 = new Term(0, "Term 1", new Date(), new Date());
    private static Term t2 = new Term(0, "Term 2", new Date(), new Date());
    private static Term t3 = new Term(0, "Term 3", new Date(), new Date());

    public static List<Term> getTerms(){
        List<Term> terms = new ArrayList<>();
        terms.add(t1);
        terms.add(t2);
        terms.add(t3);
        return terms;
    }
}
