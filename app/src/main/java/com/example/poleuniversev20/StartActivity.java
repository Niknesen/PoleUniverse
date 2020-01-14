package com.example.poleuniversev20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class StartActivity extends AppCompatActivity {
    Button day1Button;
    Button day2Button;
    Button nextButton;
    String judgeName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckJudge();
        if (judgeName == "No Judge") {
            setContentView(R.layout.judge_name_input);
            nextButton = findViewById(R.id.buttonChoreographyNext);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText dob = findViewById(R.id.judgeNameInput);
                    judgeName = dob.getText().toString();
                    SaveJudge();
                    GoFurther();
                }
            });
        } else GoFurther();
    }

    //Go to day 1,2 activity
    public void GoFurther() {
        setContentView(R.layout.day);
        Toast toast = Toast.makeText(getApplicationContext(), "Судит: " + judgeName, Toast.LENGTH_SHORT);
        toast.show();
        day1Button = findViewById(R.id.day1Button);
        day2Button = findViewById(R.id.day2Button);
        day1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent day1 = new Intent(StartActivity.this, Categories1Day.class);
                day1.putExtra("Action", getIntent().getStringExtra("Action"));
                startActivity(day1);
            }
        });
        day2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent day2 = new Intent(StartActivity.this, Categories2Day.class);
                day2.putExtra("Action", getIntent().getStringExtra("Action"));
                startActivity(day2);
            }
        });
    }

    public void SaveJudge() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("JudgeNameChoreography", judgeName);
        editor.apply();
    }

    public void CheckJudge() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        judgeName = sharedPref.getString("JudgeNameChoreography", "No Judge");
    }
}
