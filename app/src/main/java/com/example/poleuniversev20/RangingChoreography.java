package com.example.poleuniversev20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RangingChoreography extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingChoreography.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Choreography");
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SportsmenName
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_choreography);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sportsmenName = getIntent().getStringExtra("SportsmenName");
        sportsmenCategory = getIntent().getStringExtra("Сategory");
        TextView nameView = findViewById(R.id.ChoreographySportsmenName);
        nameView.setText(sportsmenName);
        buttonLoad = findViewById(R.id.rangingLoadChoreography);
        TextView category = findViewById(R.id.formCategoryChoreography);
        category.setText(sportsmenCategory);

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), PostRanging.class);
                    intent.putExtra("scores", (Serializable) GetScore()); //Put your id to your next Intent
                    intent.putExtra("Action", "Choreography");
                    intent.putExtra("Category", sportsmenCategory);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(getApplicationContext(), "Вы Заполнили не все поля", Toast.LENGTH_LONG);
                    t.show();
                }
                Log.v("Debug", "Has posted");
            }
        });
    }

    private Map<String, String> GetScore() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioChoreographyParter);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioChoreographyPlastic);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioChoreographyStatement);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioChoreographyMusic);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioChoreographyDynamic);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.editChoreographyComment);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "Choreography");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("parter", (String) radioButtonPower.getText());
        score.put("plastic", (String) radioButtonFlex.getText());
        score.put("statement", (String) radioButtonCombination.getText());
        score.put("music", (String) radioButtonDoublePole.getText());
        score.put("dynamic", (String) radioButtonBalance.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }
}
