package com.example.tbro402mobileapplication;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.tbro402mobileapplication.DB.DBClass.Assessment;
import com.example.tbro402mobileapplication.ViewModel.assessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;

import static com.example.tbro402mobileapplication.Utilities.Constants.Assessment_ID_KEY;
import static com.example.tbro402mobileapplication.Utilities.Constants.Course_ID_KEY;

public class assessmentActivity extends AppCompatActivity {
    private int course;
    private int assessment;
    private assessmentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(assessmentViewModel.class);
        Bundle intent = getIntent().getExtras();
        assessment = intent.getInt(Assessment_ID_KEY);
        course = intent.getInt(Course_ID_KEY);

        if(assessment == -1){
            viewModel.loadData(assessment);
        }
        else{
            viewModel.loadData(assessment);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EditText title = findViewById(R.id.assessmentTitle);
                    title.setText(viewModel.liveAssessment.getValue().getTitle());
                    EditText start = findViewById(R.id.start);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                    start.setText(df.format(viewModel.liveAssessment.getValue().getStartDate()));
                    EditText end = findViewById(R.id.end);
                    end.setText(df.format(viewModel.liveAssessment.getValue().getEndDate()));
                }
            },500);
        }
        setContentView(R.layout.assessment);

        FloatingActionButton can = findViewById(R.id.cancel);
        can.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveAssessment();
                finish();
            }
        });
    }

    private boolean saveAssessment() {
        EditText title = findViewById(R.id.assessmentTitle);
        EditText start = findViewById(R.id.start);
        EditText end = findViewById(R.id.end);
        RadioGroup radioType = findViewById(R.id.type);
        int perform = R.id.performance;
        int object = R.id.objective;
        String type;
        CheckBox status = findViewById(R.id.status);
        if(radioType.getCheckedRadioButtonId() == perform){
            type = "Performance";
        }else{
            type = "Objective";
        }
        if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(start.getText()) ||
                TextUtils.isEmpty(end.getText()) || (radioType.getCheckedRadioButtonId()!= perform
                && radioType.getCheckedRadioButtonId() != object)){
            Log.d("Missing field", "Check for missing fields");
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        try{
            Assessment assess = new Assessment(assessment, df.parse(start.getText().toString()),
                    df.parse(end.getText().toString()), title.getText().toString(), course,
                    type, status.isChecked());
            viewModel.saveAssessment(assess);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
