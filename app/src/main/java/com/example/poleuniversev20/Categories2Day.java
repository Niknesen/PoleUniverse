package com.example.poleuniversev20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class Categories2Day extends AppCompatActivity {
    Button category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_day_2);
        SaveDay();
    }

    public void buttonClick(View view) {
        category = findViewById(view.getId());
        Intent sportlist = new Intent(Categories2Day.this, SportsmenList.class);
        sportlist.putExtra("Category", category.getText());
        //  Log.d("Category ", (String) category.getText());
        sportlist.putExtra("Action", CheckAction());
        //  sportlist.putExtra("Day","2");
        startActivity(sportlist);
    }

    public String CheckAction() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String action = sharedPref.getString("Action", "No Action");
        return action;
    }

    public void SaveDay() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Day", "2");
        editor.apply();
    }

}