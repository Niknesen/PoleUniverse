package com.example.poleuniversev20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class Categories1Day extends AppCompatActivity {
    Button category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_day_1);
        SaveDay();
    }

    public void buttonClick(View view) {
        category = findViewById(view.getId());
        Intent sportlist = new Intent(Categories1Day.this, SportsmenList.class);
        sportlist.putExtra("Category", category.getText());
        Log.d("Category ", (String) category.getText());
        sportlist.putExtra("Action", CheckAction());
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
        editor.putString("Day", "1");
        editor.apply();
    }

}
