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

public class RangingTechnikDayTwo extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;
    boolean isSport;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingTechnikDayTwo.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Technique");
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
            setContentView(R.layout.form_technique_sport);
            TextView nameView = findViewById(R.id.TechniDancerNameSport);
            nameView.setText(sportsmenName);
            buttonLoad = findViewById(R.id.buttonSaveRangingTechnikSport);
            TextView category = findViewById(R.id.formCategoryTechnikSport);
            category.setText(sportsmenCategory);
        } else {
            setContentView(R.layout.form_technique_aerials);
            TextView nameView = findViewById(R.id.TechniDancerNameAerials);
            nameView.setText(sportsmenName);
            buttonLoad = findViewById(R.id.buttonSaveRangingTechnikAerials);
            TextView category = findViewById(R.id.formCategoryTechnikAerials);
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
                    intent.putExtra("Action", "Technique");
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
        RadioGroup radioGroupPower = findViewById(R.id.radioTechnikPowerAerials);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioTechnikFlexAerials);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioTechnikCombinationAerials);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioTechnikDoublePoleAerials);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioTechnikBalanceAerials);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        RadioGroup radioGroupDynamic = findViewById(R.id.radioTechnikDynamicAerials);
        RadioButton radioButtonDynamic = findViewById(radioGroupDynamic.getCheckedRadioButtonId());
        //Name of PoleDancer
        TextView TechniDancerNameView = findViewById(R.id.TechniDancerNameAerials);

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.TechnikJudgeCommentAerials);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "TechnikAerials");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("power", (String) radioButtonPower.getText());
        score.put("flex", (String) radioButtonFlex.getText());
        score.put("combination", (String) radioButtonCombination.getText());
        score.put("doublepole", (String) radioButtonDoublePole.getText());
        score.put("balance", (String) radioButtonBalance.getText());
        score.put("dynamic", (String) radioButtonDynamic.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }

    private Map<String, String> GetScoreSport() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioTechnikPowerSport);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioTechnikFlexSport);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioTechnikCombinationSport);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioTechnikDoublePoleSport);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioTechnikBalanceSport);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        RadioGroup radioGroupDynamic = findViewById(R.id.radioTechnikDynamicSport);
        RadioButton radioButtonDynamic = findViewById(radioGroupDynamic.getCheckedRadioButtonId());
        //Name of PoleDancer
        TextView TechniDancerNameView = findViewById(R.id.TechniDancerNameSport);

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.TechnikJudgeCommentSport);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "TechnikSport");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("power", (String) radioButtonPower.getText());
        score.put("flex", (String) radioButtonFlex.getText());
        score.put("combination", (String) radioButtonCombination.getText());
        score.put("doublepole", (String) radioButtonDoublePole.getText());
        score.put("balance", (String) radioButtonBalance.getText());
        score.put("dynamic", (String) radioButtonDynamic.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }

}