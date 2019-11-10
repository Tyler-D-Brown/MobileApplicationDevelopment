package com.example.tbro402mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.ViewModel.TermDetailsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;

public class termDetailsActivity extends AppCompatActivity {
    private TermDetailsModel termDetailsModel;
    private List<Course> courseData;
    private boolean tNewTerm;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        termDetailsModel = ViewModelProviders.of(this).get(TermDetailsModel.class);
        Bundle intent = getIntent().getExtras();
        int termId = intent.getInt(Term_ID_KEY);
        if(termId == -1) {
            tNewTerm = true;
            termDetailsModel.loadData(termId);

        } else {
            tNewTerm = false;
            termDetailsModel.loadData(termId);
            EditText termTitle = findViewById(R.id.termTitle);
            termTitle.setText(termDetailsModel.liveTerm.getValue().getTitle());
            EditText startDate = findViewById(R.id.startDate);
            startDate.setText(termDetailsModel.liveTerm.getValue().getStartDate().toString());
            EditText endDate = findViewById(R.id.startDate);
            endDate.setText(termDetailsModel.liveTerm.getValue().getEndDate().toString());
        }
        setContentView(R.layout.term_details);
        initViewModel();
        FloatingActionButton add = findViewById(R.id.add_course);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, termDetailsActivity.class);
                intent.putExtra(Term_ID_KEY, -1);
                try {
                    if(saveTerm()) {
                        context.startActivity(intent);
                    } else {

                    }
                }
                catch(Exception e){
                    Log.d("except", e.toString());
                }
            }
        });
        FloatingActionButton save = findViewById(R.id.save_term);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndReturn();
            }
        });
        FloatingActionButton cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViewModel() {
        termDetailsModel = ViewModelProviders.of(this).get(TermDetailsModel.class);
        final Observer<List<Course>> courseObserver = new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                if(courseData !=null) {
                    courseData.clear();
                }
                if(!courses.isEmpty()){
                    courseData.addAll(courses);
                }
                if(courseData != null) {
                    for (int i = 0; i < courseData.size(); i++) {
                        insertCourseRow(courseData.get(i));
                    }
                }
            }
        };
        termDetailsModel.termCourses.observe(this, courseObserver);
    }



    private void insertCourseRow(Course add){
        LinearLayout contain = findViewById(R.id.courseContainer);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newCourseRow = inflater.inflate(R.layout.summary_card, null);
        Button button = newCourseRow.findViewById(R.id.title);
        button.setText(add.getTitle());
        EditText start = newCourseRow.findViewById(R.id.startDate);
        start.setText(add.getStartDate().toString());
        EditText end = newCourseRow.findViewById(R.id.endDate);
        end.setText(add.getEndDate().toString());

        ViewGroup insert = contain;
        insert.addView(newCourseRow);
    }

    private void saveAndReturn(){
        Bundle intent = getIntent().getExtras();
        int termId = intent.getInt(Term_ID_KEY);
        EditText termTitle = findViewById(R.id.termTitle);
        EditText termStartDate = findViewById(R.id.startDateText);
        EditText termEndDate = findViewById(R.id.endDateText);
        if(TextUtils.isEmpty(termTitle.getText().toString().trim()) ||
                TextUtils.isEmpty(termStartDate.getText().toString().trim()) ||
                TextUtils.isEmpty(termEndDate.getText().toString().trim())){
            return;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        try{
            Term t = new Term(termId, termTitle.getText().toString(),
                    df.parse(termStartDate.getText().toString()),
                    df.parse(termEndDate.getText().toString()));
            Log.i(TAG, "term details: ");
            Log.i(TAG, "term Title: " + t.getTitle());
            Log.i(TAG, "term Start: " + t.getStartDate());
            Log.i(TAG, "term End: " + t.getEndDate());
            Log.i(TAG, "term ID: " + t.getId());
            termDetailsModel.saveTerm(t);
            finish();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean saveTerm() {
        Bundle intent = getIntent().getExtras();
        int termId = intent.getInt(Term_ID_KEY);
        EditText termTitle = findViewById(R.id.termTitle);
        EditText termStartDate = findViewById(R.id.startDateText);
        EditText termEndDate = findViewById(R.id.endDateText);
        if(TextUtils.isEmpty(termTitle.getText().toString()) ||
                TextUtils.isEmpty(termStartDate.getText().toString()) ||
                TextUtils.isEmpty(termEndDate.getText().toString())){
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        try {
            Term t = new Term(termId, termTitle.getText().toString(),
                    df.parse(termStartDate.getText().toString()),
                    df.parse(termEndDate.getText().toString()));
            termDetailsModel.saveTerm(t);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean checkTerm(){
        return false;
    }
}
