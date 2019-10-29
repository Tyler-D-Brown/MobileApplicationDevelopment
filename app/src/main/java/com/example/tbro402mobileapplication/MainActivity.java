package com.example.tbro402mobileapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.ViewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.example.tbro402mobileapplication.termDetailsActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import static com.example.tbro402mobileapplication.Utilities.Constants.Term_ID_KEY;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private List<Term> termData;
    //TODO validate that the context is correct.
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        initViewModel();

        FloatingActionButton fab = findViewById(R.id.add_term);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, termDetailsActivity.class);
                intent.putExtra(Term_ID_KEY, -1);
                try {
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("except", e.toString());
                }
            }
        });


    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver = new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable List<Term> terms) {
                if(termData !=null) {
                    termData.clear();
                }
                if(!terms.isEmpty()){
                    termData.addAll(terms);
                }
                if(termData != null) {
                    for (int i = 0; i < termData.size(); i++) {
                        insertTermRow(termData.get(i));
                    }
                }
            }
        };
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.terms.observe(this, termObserver);
    }

    private void insertTermRow(final Term add){
        LinearLayout contain = findViewById(R.id.termContainer);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newTermRow = inflator.inflate(R.layout.summary_card, null);
        Button button = newTermRow.findViewById(R.id.title);
        button.setText(add.getTitle());
        EditText start = newTermRow.findViewById(R.id.startDate);
        start.setText(add.getStartDate().toString());
        EditText end = newTermRow.findViewById(R.id.endDate);
        end.setText(add.getEndDate().toString());
        button.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View termButton) {
                          Intent intent = new Intent(getBaseContext(), termDetailsActivity.class);
                          intent.putExtra(Term_ID_KEY, add.getId());
                          Snackbar.make(termButton, "Replace with your own action", Snackbar.LENGTH_LONG)
                                  .setAction("Action", null).show();
                      }
                  });

        ViewGroup insert = contain;
        insert.addView(newTermRow);
    }
}
