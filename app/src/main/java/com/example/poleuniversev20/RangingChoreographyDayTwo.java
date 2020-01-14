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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangingChoreographyDayTwo extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;
    boolean isSport;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingChoreographyDayTwo.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Choreography");
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sportsmenName = getIntent().getStringExtra("SportsmenName");
        sportsmenCategory = getIntent().getStringExtra("Сategory");
        Pattern pattern = Pattern.compile(".*SPORT.*");
        Matcher matcher = pattern.matcher(sportsmenCategory);
        isSport = matcher.find();
        if (isSport) {
            setContentView(R.layout.form_choreography_sport);
            TextView nameView = findViewById(R.id.ChoreographySportsmenNameSport);
            nameView.setText(sportsmenName);
            buttonLoad = findViewById(R.id.rangingLoadChoreographySport);
            TextView category = findViewById(R.id.formCategoryChoreographySport);
            category.setText(sportsmenCategory);
        } else {
            setContentView(R.layout.form_choreography_aerials);
            TextView nameView = findViewById(R.id.ChoreographySportsmenNameAerilas);
            nameView.setText(sportsmenName);
            buttonLoad = findViewById(R.id.rangingLoadChoreographyAerilas);
            TextView category = findViewById(R.id.formCategoryChoreographyAerilas);
            category.setText(sportsmenCategory);
        }
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), PostRanging.class);
                    if (isSport)
                        intent.putExtra("scores", (Serializable) GetScoreSport()); //Put your id to your next Intent
                    else intent.putExtra("scores", (Serializable) GetScoreAerials());
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

    private Map<String, String> GetScoreAerials() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioChoreographyParterAerilas);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioChoreographyPlasticAerilas);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioChoreographyStatementAerilas);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioChoreographyDynamicAerilas);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.editChoreographyCommentAerilas);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "ChoreographyAerials");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("parter", (String) radioButtonPower.getText());
        score.put("plastic", (String) radioButtonFlex.getText());
        score.put("statement", (String) radioButtonCombination.getText());
        score.put("dynamic", (String) radioButtonBalance.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }

    private Map<String, String> GetScoreSport() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioChoreographyParterSport);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioChoreographyPlasticSport);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioChoreographyStatementSport);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioChoreographyMusicSport);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioChoreographyDynamicSport);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.editChoreographyCommentSport);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "ChoreographySport");
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
