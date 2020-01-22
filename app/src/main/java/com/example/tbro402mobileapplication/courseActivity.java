package com.example.tbro402mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.ViewModel.courseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static com.example.tbro402mobileapplication.Utilities.Constants.Assessment_ID_KEY;
import static com.example.tbro402mobileapplication.Utilities.Constants.Course_ID_KEY;
import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;
import static com.example.tbro402mobileapplication.Utilities.Constants.MENTOR_ID_KEY;

public class courseActivity extends AppCompatActivity {
    private courseModel viewModel;
    private List<Assessment> assessmentsData = new ArrayList<>();
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(courseModel.class);
        Bundle intent = getIntent().getExtras();
        final int courseID = intent.getInt(Course_ID_KEY);
        if(courseID == -1) {
            viewModel.loadData(courseID);
        } else {
            Log.i(TAG, "intent received: " + courseID);
            viewModel.loadData(courseID);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewModel.loadMentor();
                    EditText Title = findViewById(R.id.termTitle);
                    Title.setText(viewModel.liveCourse.getValue().getTitle());
                    EditText startDate = findViewById(R.id.startDateText);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                    startDate.setText(df.format(viewModel.liveCourse.getValue().getStartDate()));
                    EditText endDate = findViewById(R.id.endDateText);
                    endDate.setText(df.format(viewModel.liveCourse.getValue().getEndDate()));
                    EditText note = findViewById(R.id.note);
                    note.setText(viewModel.liveCourse.getValue().getNote());
                    try {
                        if(viewModel.liveCourse.getValue().getMentor()==-1){
                            Button b = findViewById(R.id.editMentor);
                            b.setText("Add Mentor");
                        }else {
                            TextView mentName = findViewById(R.id.mentorName);
                            mentName.setText(viewModel.mentor.getName());
                            TextView mentPhone = findViewById(R.id.mentorPhone);
                            mentPhone.setText(viewModel.mentor.getPhone());
                            TextView mentEmail = findViewById(R.id.mentorEmail);
                            mentEmail.setText(viewModel.mentor.getEmail());
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "mentor doesn't exist");
                    }
                    initViewModel();
                    Date currentTime = Calendar.getInstance().getTime();
                    String date = df.format(currentTime);
                    Log.i(TAG, date);
                    Log.i(TAG, df.format(viewModel.liveCourse.getValue().getStartDate()));
                    if(date.equals(df.format(viewModel.liveCourse.getValue().getStartDate()))){
                        Toast.makeText(context, "Course Starts Today", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Course Start today");
                    }
                    if(date.equals(df.format(viewModel.liveCourse.getValue().getEndDate()))){
                        Toast.makeText(context, "Course Ends Today", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Course ends today");
                    }
                }
            }, 500);
        }
        setContentView(R.layout.course);
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(assessmentsData.size()<5) {
                    if (courseID == -1) {
                        Toast.makeText(context, "Please save the course before adding an Assessment.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, assessmentActivity.class);
                    intent.putExtra(Assessment_ID_KEY, -1);
                    intent.putExtra(Course_ID_KEY, courseID);
                    try {
                        if (saveCourse()) {
                            context.startActivity(intent);
                        } else {
                            Log.d("Course not saved", "Save Failed");
                        }
                    } catch (Exception e) {
                        Log.d("except", e.toString());
                    }
                }else{
                    Toast.makeText(context, "only 5 assessments are allowed per course", Toast.LENGTH_LONG).show();
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
        Button mentor = findViewById(R.id.editMentor);
        mentor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(courseID == -1){
                    Toast.makeText(context, "Please save the course before adding a Mentor.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(context, mentorActivity.class);
                intent.putExtra(MENTOR_ID_KEY, viewModel.mentor.getId());
                intent.putExtra(Course_ID_KEY, viewModel.liveCourse.getValue().getId());
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("exception", e.toString());
                }
            }
        });
        FloatingActionButton share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText note = findViewById(R.id.note);
                if(note.getText()==null){
                    Toast.makeText(context,"no note to share", Toast.LENGTH_SHORT).show();
                }else{
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setData(Uri.parse("sms:"));
                    smsIntent.putExtra("sms_body", note.getText().toString());
                    startActivity(smsIntent);
                }
            }
        });

        try {
            ProgressBar degreeProgress = findViewById(R.id.progressBar);
            int completed = viewModel.getCompletedAssess(courseID);
            int assessments = viewModel.getAssess(courseID);
            double progress = (double)completed/assessments;
            progress = progress*100;
            degreeProgress.setProgress((int)progress);
        }catch(Exception e){
            Log.i("Danger Will Robinson", e.toString());
        }
    }

    private boolean saveCourse() {
        Bundle intent = getIntent().getExtras();
        int courseId = intent.getInt(Course_ID_KEY);
        int termId = intent.getInt(Term_ID_KEY);
        Log.i(TAG, "Term ID: " + termId + " courseId: " + courseId);
        EditText termTitle = findViewById(R.id.termTitle);
        EditText termStartDate = findViewById(R.id.startDateText);
        EditText termEndDate = findViewById(R.id.endDateText);
        EditText note = findViewById(R.id.note);
        if(TextUtils.isEmpty(termTitle.getText().toString()) ||
                TextUtils.isEmpty(termStartDate.getText().toString()) ||
                TextUtils.isEmpty(termEndDate.getText().toString())){
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        try {
            Course c;
            if(viewModel.mentor.getId() != -1) {
                c = new Course(termTitle.getText().toString(), courseId,
                        df.parse(termStartDate.getText().toString()),
                        df.parse(termEndDate.getText().toString()), termId, "Enrolled",
                        note.getText().toString(), viewModel.mentor.getId());
            }else{
                c = new Course(termTitle.getText().toString(), courseId,
                        df.parse(termStartDate.getText().toString()),
                        df.parse(termEndDate.getText().toString()), termId, "Enrolled",
                        note.getText().toString(), -1);
            }
            viewModel.saveCourse(c);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
                        Log.e(TAG, "assessment Title: " + assessmentsData.get(i).getTitle());
                        insertAssessmentRow(assessmentsData.get(i));
                    }
                }
            }
        };
        viewModel.courseAssessments.observe(this, assessmentObserver);
    }

    private void insertAssessmentRow(final Assessment add){
        LinearLayout contain = findViewById(R.id.courseContainer);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newRow = inflator.inflate(R.layout.summary_card, null);
        Button button = newRow.findViewById(R.id.title);
        button.setText(add.getTitle());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        EditText start = newRow.findViewById(R.id.startDate);
        String date = df.format(add.getStartDate());
        start.setText(date);
        EditText end = newRow.findViewById(R.id.endDate);
        date = df.format(add.getEndDate());
        end.setText(date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View Button) {
                Intent intent = new Intent(getBaseContext(), assessmentActivity.class);
                intent.putExtra(Assessment_ID_KEY, add.getId());
                try {
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("except: ", e.toString());
                }
            }
        });
        FloatingActionButton delete = newRow.findViewById(R.id.delete);
        newRow.setId(add.getId());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View delete) {
                viewModel.deleteAssessment(add.getId());
                View assessRow = findViewById(R.id.courseContainer).findViewById(add.getId());
                ((ViewGroup)assessRow.getParent()).removeView(assessRow);
            }
        });
        ViewGroup insert = contain;
        View termRow = insert.findViewById(add.getId());
        if (termRow == null) {
            insert.addView(newRow);
        } else {
            ((ViewGroup)termRow.getParent()).removeView(termRow);
            insert.addView(newRow);
        }
    }
}
