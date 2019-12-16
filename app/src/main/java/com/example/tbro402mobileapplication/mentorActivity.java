package com.example.tbro402mobileapplication;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.tbro402mobileapplication.DB.DBClass.Mentor;
import com.example.tbro402mobileapplication.ViewModel.mentorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.tbro402mobileapplication.Utilities.Constants.MENTOR_ID_KEY;

public class mentorActivity extends AppCompatActivity {
    private mentorViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(mentorViewModel.class);
        Bundle intent = getIntent().getExtras();
        int mentor = intent.getInt(MENTOR_ID_KEY);

        if(mentor == -1){
            viewModel.loadData(mentor);
        }
        else{
            viewModel.loadData(mentor);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EditText name = findViewById(R.id.mentorName);
                    name.setText(viewModel.liveMentor.getValue().getName());
                    EditText phone = findViewById(R.id.phoneText);
                    phone.setText(viewModel.liveMentor.getValue().getPhone());
                    EditText email = findViewById(R.id.emailText);
                    email.setText(viewModel.liveMentor.getValue().getEmail());
                }
            },500);
        }
        setContentView(R.layout.mentor);
//        Log.d("Cancel Mentor ID: ", Integer.toString(findViewById(R.id.cancelMentor).getId()));

        FloatingActionButton can = findViewById(R.id.cancelMentor);
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
                saveMentor();
                finish();
            }
        });
    }

    private boolean saveMentor() {
        Bundle intent = getIntent().getExtras();
        int mentor = intent.getInt(MENTOR_ID_KEY);
        EditText name = findViewById(R.id.mentorName);
        EditText phone = findViewById(R.id.phoneText);
        EditText email = findViewById(R.id.emailText);
        if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(phone.getText()) ||
                TextUtils.isEmpty(email.getText())){
            return false;
        }
        try{
            Mentor ment = new Mentor(mentor, name.getText().toString(),
                    email.getText().toString(), phone.getText().toString());
            viewModel.saveMentor(ment);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
