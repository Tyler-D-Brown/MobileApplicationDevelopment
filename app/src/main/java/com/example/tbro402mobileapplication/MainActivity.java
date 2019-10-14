package com.example.tbro402mobileapplication;

import android.content.Context;
import android.os.Bundle;

import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.example.tbro402mobileapplication.ViewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private List<Term> termData = mainViewModel.terms.getValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        initViewModel();

        FloatingActionButton fab = findViewById(R.id.add_term);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver = new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                termData.clear();
                termData.addAll(terms);

                if(mainViewModel.terms != null) {
                    for (int i = 0; i < termData.size(); i++) {
                        insertTermRow(termData.get(i));
                    }
                }
            }
        };
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.terms.observe(this, termObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertTermRow(Term add){
        LinearLayout contain = findViewById(R.id.termContainer);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newTermRow = inflator.inflate(R.layout.summary_card, null);
        Button but = newTermRow.findViewById(R.id.term);
        but.setText(add.getTitle());
        EditText start = newTermRow.findViewById(R.id.startDate);
        start.setText(add.getStartDate().toString());
        EditText end = newTermRow.findViewById(R.id.endDate);
        end.setText(add.getEndDate().toString());

        ViewGroup insert = contain;
        insert.addView(newTermRow);
    }
}
