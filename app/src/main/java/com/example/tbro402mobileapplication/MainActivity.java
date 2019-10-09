package com.example.tbro402mobileapplication;

import android.content.Context;
import android.os.Bundle;

import com.example.tbro402mobileapplication.DB.DBClass.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        Term[] terms = {(new Term(0, "Term 1", new Date(), new Date())), (new Term(0, "Term 2", new Date(), new Date())), (new Term(0, "Term 3", new Date(), new Date()))};
        Log.d("terms object", terms[0].getTitle() + terms[1].getTitle() + terms[2].getTitle());

        for(int i = 0; i<terms.length; i++){
            insertTermRow(terms[i]);
        }

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


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

        View newTermRow = inflator.inflate(R.layout.term_card, null);
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
