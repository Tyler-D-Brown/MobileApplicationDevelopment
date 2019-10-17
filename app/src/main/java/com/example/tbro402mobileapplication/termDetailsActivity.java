package com.example.tbro402mobileapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.ViewModel.MainViewModel;
import com.example.tbro402mobileapplication.ViewModel.TermDetailsModel;

import java.util.List;

public class termDetailsActivity extends AppCompatActivity {
    private TermDetailsModel t;
    private List<Course> courseData = t.termCourses.getValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_details);
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<Course>> termObserver = new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                courseData.clear();
                courseData.addAll(courses);

                if(t.termCourses != null) {
                    for (int i = 0; i < courseData.size(); i++) {
                        insertCourseRow(courseData.get(i));
                    }
                }
            }
        };
        t = ViewModelProviders.of(this).get(TermDetailsModel.class);
        t.termCourses.observe(this, termObserver);
    }

    private void insertCourseRow(Course add){
        LinearLayout contain = findViewById(R.id.courseContainer);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newCourseRow = inflator.inflate(R.layout.summary_card, null);
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
