package com.example.tbro402mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.ViewModel.TermDetailsModel;
import com.example.tbro402mobileapplication.ViewModel.courseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.tbro402mobileapplication.Utilities.Constants.Assessment_ID_KEY;
import static com.example.tbro402mobileapplication.Utilities.Constants.Course_ID_KEY;
import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;

public class courseActivity extends AppCompatActivity {
    private courseModel viewModel;
    private List<Assessment> assessmentsData = new ArrayList<>();
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(courseModel.class);
        Bundle intent = getIntent().getExtras();
        int courseID = intent.getInt(Course_ID_KEY);
        if(courseID == -1) {
            viewModel.loadData(courseID);

        } else {
            Log.i(TAG, "intent received: " + courseID);
            viewModel.loadData(courseID);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "termID: " + viewModel.liveCourse.getValue().getId());
                    EditText Title = findViewById(R.id.termTitle);
                    Log.i(TAG, "termTitle: " + viewModel.liveCourse.getValue().getTitle());
                    Title.setText(viewModel.liveCourse.getValue().getTitle());
                    EditText startDate = findViewById(R.id.startDateText);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                    startDate.setText(df.format(viewModel.liveCourse.getValue().getStartDate()));
                    EditText endDate = findViewById(R.id.endDateText);
                    endDate.setText(df.format(viewModel.liveCourse.getValue().getEndDate()));
                }
            }, 500);
        }
        setContentView(R.layout.course);
        initViewModel();
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, assessmentActivity.class);
                intent.putExtra(Assessment_ID_KEY, -1);
                try {
                    if(saveCourse()) {
                        context.startActivity(intent);
                    } else {
                        Log.d("Course not saved", "Save Failed");
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
                saveCourse();
                finish();
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

    private boolean saveCourse() {

        return true;
    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(courseModel.class);
        final Observer<List<Assessment>> assessmentObserver = new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable List<Assessment> assessments) {
                assessmentsData.clear();
                assessmentsData.addAll(assessments);
                if(assessmentsData != null) {
                    for (int i = 0; i < assessmentsData.size(); i++) {
                        insertAssessmentRow(assessmentsData.get(i));
                    }
                }
            }
        };
        viewModel.courseAssessments.observe(this, assessmentObserver);
    }

    private void insertAssessmentRow(final Assessment add){
        final LinearLayout contain = findViewById(R.id.termContainer);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newTermRow = inflator.inflate(R.layout.summary_card, null);
        Button button = newTermRow.findViewById(R.id.title);
        button.setText(add.getTitle());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        EditText start = newTermRow.findViewById(R.id.startDate);
        String date = df.format(add.getStartDate());
        start.setText(date);
        EditText end = newTermRow.findViewById(R.id.endDate);
        date = df.format(add.getEndDate());
        end.setText(date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View termButton) {
                Intent intent = new Intent(getBaseContext(), assessmentActivity.class);
                intent.putExtra(Assessment_ID_KEY, add.getId());
                try {
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("except", e.toString());
                }
            }
        });
        FloatingActionButton delete = newTermRow.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View delete) {
                viewModel.deleteAssessment(add.getId());
                View termRow = findViewById(R.id.termContainer).findViewById(add.getId());
                ((ViewGroup)termRow.getParent()).removeView(termRow);
            }
        });
        newTermRow.setId(add.getId());
        ViewGroup insert = contain;
        View termRow = insert.findViewById(add.getId());
        if (termRow == null) {
            insert.addView(newTermRow);
        } else {
            ((ViewGroup)termRow.getParent()).removeView(termRow);
            insert.addView(newTermRow);
        }
    }
}
