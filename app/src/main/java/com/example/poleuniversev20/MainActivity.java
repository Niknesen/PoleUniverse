package com.example.poleuniversev20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonChoreography, buttonTechnique, buttonArtisctic, buttonPenalty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonChoreography = findViewById(R.id.buttonChoreography);
        buttonTechnique = findViewById(R.id.buttonTechnique);
        buttonArtisctic = findViewById(R.id.buttonArtisctic);
        buttonPenalty = findViewById(R.id.buttonPenalty);

        buttonTechnique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TechniqueIntent = new Intent(MainActivity.this, StartActivity.class);
                SaveAction("Technique");
                startActivity(TechniqueIntent);
            }
        });
        buttonChoreography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent choreographyIntent = new Intent(MainActivity.this, StartActivity.class);
                SaveAction("Choreography");
                startActivity(choreographyIntent);
            }
        });
        buttonArtisctic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artisticIntent = new Intent(MainActivity.this, StartActivity.class);
                SaveAction("Artistic");
                startActivity(artisticIntent);
            }
        });
        buttonPenalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent penaltyIntent = new Intent(MainActivity.this, StartActivity.class);
                SaveAction("Penalty");
                startActivity(penaltyIntent);
            }
        });
    }

    //Adding option to clear the Judge name
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_judge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeJudgeName:
                clearJudgeName();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearJudgeName() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        Toast toast = Toast.makeText(getApplicationContext(), "Выберите вид судейства", Toast.LENGTH_LONG);
        toast.show();
    }

    public void SaveAction(String action) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Action", action);
        editor.apply();
    }
}
