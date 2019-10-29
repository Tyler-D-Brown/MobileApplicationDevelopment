package com.example.tbro402mobileapplication;

import android.content.Context;
import android.os.Bundle;
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
import com.example.tbro402mobileapplication.ViewModel.MainViewModel;
import com.example.tbro402mobileapplication.ViewModel.TermDetailsModel;

import java.util.List;

import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;

public class termDetailsActivity extends AppCompatActivity {
    private TermDetailsModel termDetailsModel;
    private List<Course> courseData;
    private boolean tNewTerm = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle intent = getIntent().getExtras();
        termDetailsModel = ViewModelProviders.of(this).get(TermDetailsModel.class);
        int termId = intent.getInt(Term_ID_KEY);
        if(termId == -1) {
            boolean tNewTerm = true;
            EditText termTitle = findViewById(R.id.termTitle);
            termDetailsModel.loadData(termId);
            //termTitle.setText("Term Title");
        } else {
            termDetailsModel.loadData(termId);
        }
        /*EditText termTitle = findViewById(R.id.termTitle);
        EditText termStartDate = findViewById(R.id.startDateText);
        EditText termEndDate = findViewById(R.id.endDateText);*/
        setContentView(R.layout.term_details);
        initViewModel();
    }

    private void initViewModel() {
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
        termDetailsModel = ViewModelProviders.of(this).get(TermDetailsModel.class);
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
}
