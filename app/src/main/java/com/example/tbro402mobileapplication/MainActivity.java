package com.example.tbro402mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.tbro402mobileapplication.DB.DBClass.Course;
import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.ViewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private List<Term> termData = new ArrayList<>();
    public List<Course> termCourses = new ArrayList<>();
    private final Context context = this;
    private final LifecycleOwner owner = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        initViewModel();
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, termDetailsActivity.class);
                int id = -1;
                intent.putExtra(Term_ID_KEY, id);
                try {
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("except", e.toString());
                }
            }
        });
        try {
            ProgressBar degreeProgress = findViewById(R.id.progressBar);
            int completed = mainViewModel.getCompletedAssess();
            Log.i("complete", "completed " + completed);
            int assessments = mainViewModel.getAssess();
            Log.i("total", ""+assessments);
            double progress = (double)completed/assessments;
            Log.i("progress percent", "progress " + progress);
            progress = progress*100;
            Log.i("progress percent", "progress " + progress);
            degreeProgress.setProgress((int)progress);
        }catch(Exception e){
            Log.i("Danger Will Robinson", e.toString());
        }
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final Observer<List<Term>> termObserver = new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable List<Term> terms) {
                termData.clear();
                termData.addAll(terms);
                if(termData != null) {
                    for (int i = 0; i < termData.size(); i++) {
                        insertTermRow(termData.get(i));
                    }
                }
            }
        };
        mainViewModel.terms.observe(this, termObserver);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Intent x = new Intent(MainActivity.this, MainActivity.class);
        finish();
        startActivity(x);
    }

    private void insertTermRow(final Term add){
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
                          Intent intent = new Intent(getBaseContext(), termDetailsActivity.class);
                          intent.putExtra(Term_ID_KEY, add.getId());
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
                            mainViewModel.getCourses(add.getId());
                            final Observer<List<Course>> courseObserver = new Observer<List<Course>>() {
                                @Override
                                public void onChanged(@Nullable List<Course> courses) {
                                    termCourses.clear();
                                    termCourses.addAll(courses);
                                    if(termCourses.size() == 0){
                                        mainViewModel.deleteTerm(add.getId());
                                        View termRow = findViewById(R.id.termContainer).findViewById(add.getId());
                                        ((ViewGroup)termRow.getParent()).removeView(termRow);
                                    }else{
                                        Toast.makeText(context, "Please delete courses before deleting term", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            mainViewModel.courses.observe(owner, courseObserver);
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



















