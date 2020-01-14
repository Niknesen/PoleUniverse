package com.example.poleuniversev20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class RangingArtisticDayTwo extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;
    TextView category;
    boolean isSport;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingArtisticDayTwo.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Artistic");
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SportsmenName
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sportsmenName = getIntent().getStringExtra("SportsmenName");
        sportsmenCategory = getIntent().getStringExtra("Сategory");
        Pattern pattern = Pattern.compile(".*SPORT.*");
        Matcher matcher = pattern.matcher(sportsmenCategory);
        isSport = matcher.find();
        if (isSport) {
            setContentView(R.layout.form_artistic_sport);
            TextView nameView = findViewById(R.id.ArtisticSportSportsmenName);
            nameView.setText(sportsmenName);
            buttonLoad = findViewById(R.id.rangingLoadArtisticSport);
            TextView category = findViewById(R.id.formCategoryArtisticSport);
            category.setText(sportsmenCategory);
        } else {
            setContentView(R.layout.form_artistic_aerials);
            TextView nameView = findViewById(R.id.ArtisticAerialsSportsmenName);
            nameView.setText(sportsmenName);
            buttonLoad = findViewById(R.id.rangingLoadArtisticAerials);
            TextView category = findViewById(R.id.formCategoryAerialArtsic);
            category.setText(sportsmenCategory);
        }

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostRanging.class);
                try {
                    if (isSport)
                        intent.putExtra("scores", (Serializable) GetScoreSport()); //Put your id to your next Intent
                    else intent.putExtra("scores", (Serializable) GetScoreAerials());
                    intent.putExtra("Action", "Artistic");
                    intent.putExtra("Category", sportsmenCategory);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(), "Вы Заполнили не все поля", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    private Map<String, String> GetScoreAerials() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioArtisticAerialsEmotions);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioArtisticAerialsSuit);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioArtisticAerialsIdea);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioArtisticAerialsStage);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.editArtisticAerialsComment);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "ArtisticAerials");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("emotions", (String) radioButtonPower.getText());
        score.put("suit", (String) radioButtonFlex.getText());
        score.put("idea", (String) radioButtonDoublePole.getText());
        score.put("stage", (String) radioButtonBalance.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }

    private Map<String, String> GetScoreSport() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioArtisticSportEmotions);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioArtisticSportSuit);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioArtisticSportStage);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.editArtisticSportComment);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "ArtisticSport");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("emotions", (String) radioButtonPower.getText());
        score.put("suit", (String) radioButtonFlex.getText());
        score.put("stage", (String) radioButtonBalance.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }


}
