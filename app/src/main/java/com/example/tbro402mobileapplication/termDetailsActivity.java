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

import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;

public class termDetailsActivity extends AppCompatActivity {
    private TermDetailsModel t;
    private List<Course> courseData = t.termCourses.getValue();
    private boolean tNewTerm = false;
    private EditText termTitle = findViewById(R.id.termTitle);
    private EditText termStartDate = findViewById(R.id.startDateText);
    private EditText termEndDate = findViewById(R.id.endDateText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_details);
        initViewModel();
    }

    private void initViewModel() {
        t = ViewModelProviders.of(this).get(TermDetailsModel.class);
        Bundle intent = getIntent().getExtras();
        if(intent == null) {
            boolean tNewTerm = true;
            termTitle.setText("Term Title");
        } else {
                int termId = intent.getInt(Term_ID_KEY);
                t.loadData(termId);
        }
        final Observer<Term> termObserver = new Observer<Term>() {
            @Override
            public void onChanged(Term currentTerm) {
                courseData.clear();
                courseData.addAll(t.termCourses.getValue());
                termTitle.setText(t.liveTerm.getValue().getTitle());
                termStartDate.setText(t.liveTerm.getValue().getStartDate().toString());
                termEndDate.setText(t.liveTerm.getValue().getEndDate().toString());

                if(courseData != null) {
                    for (int i = 0; i < courseData.size(); i++) {
                        insertCourseRow(courseData.get(i));
                    }
                }
            }
        };
        t.liveTerm.observe(this, termObserver);
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
