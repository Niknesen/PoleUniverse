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
import android.widget.Switch;
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

public class RangingPenalty extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;
    boolean isExotic, isSport, isAerials;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingPenalty.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Penalty");
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Penalty", "TEST111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_penalty);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sportsmenName = getIntent().getStringExtra("SportsmenName");
        sportsmenCategory = getIntent().getStringExtra("Сategory");

        TextView nameView = findViewById(R.id.PenaltySportsmenName);
        nameView.setText(sportsmenName);
        buttonLoad = findViewById(R.id.rangingLoadPenalty);

        TextView category = findViewById(R.id.formCategoryPenalty);
        category.setText(sportsmenCategory);
        Pattern pattern = Pattern.compile(".*Exotic.*");
        Matcher matcher = pattern.matcher(sportsmenCategory);
        isExotic = matcher.find();

        pattern = Pattern.compile(".*SPORT.*");
        matcher = pattern.matcher(sportsmenCategory);
        isSport = matcher.find();

        pattern = Pattern.compile(".*AERIALS.*");
        matcher = pattern.matcher(sportsmenCategory);
        isAerials = matcher.find();

        pattern = Pattern.compile(".*HOOP.*");
        matcher = pattern.matcher(sportsmenCategory);
        if (!isAerials) isAerials = matcher.find();


        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), PostRanging.class);
                    intent.putExtra("scores", (Serializable) GetScore()); //Put your id to your next Intent
                    intent.putExtra("Action", "Penalty");
                    intent.putExtra("Category", sportsmenCategory);
                    startActivity(intent);
                    Log.v("Debug", "Has posted");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(getApplicationContext(), "Вы Заполнили не все поля", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    private Map<String, String> GetScore() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioPenaltyStretching);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioPenaltyView);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioPenaltyFall);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioPenaltySlip);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioPenaltyDisbalance);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        RadioGroup radioGroupWiping = findViewById(R.id.radioPenaltyWiping);
        RadioButton radioButtonWiping = findViewById(radioGroupWiping.getCheckedRadioButtonId());

        RadioGroup radioGroupRepeats = findViewById(R.id.radioPenaltyRepeats);
        RadioButton radioButtonRepeats = findViewById(radioGroupRepeats.getCheckedRadioButtonId());
        // Switches

        Switch switch1 = findViewById(R.id.switch1);
        Switch switch2 = findViewById(R.id.switch2);
        Switch switch3 = findViewById(R.id.switch3);
        Switch switch4 = findViewById(R.id.switch4);

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.PenaltyJudgeComment);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();

        score.put("category", sportsmenCategory);
        if (isSport) score.put("action", "PenaltySport");
        else if (isAerials) score.put("action", "PenaltyAerials");
        else score.put("action", "Penalty");
        Log.v("Penalty", "Sport -  " + isSport + "  Aerials - " + isAerials);
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("emotions", (String) radioButtonPower.getText());
        score.put("suit", (String) radioButtonFlex.getText());
        score.put("character", (String) radioButtonCombination.getText());
        score.put("idea", (String) radioButtonDoublePole.getText());
        score.put("stage", (String) radioButtonBalance.getText());
        score.put("wiping", (String) radioButtonWiping.getText());
        score.put("repeats", (String) radioButtonRepeats.getText());
        score.put("switch1", (switch1.isChecked() ? "1" : "0"));
        if (isExotic) score.put("switch2", "0");
        else score.put("switch2", (switch2.isChecked() ? "1" : "0"));
        score.put("switch3", (switch3.isChecked() ? "1" : "0"));
        score.put("switch4", (switch4.isChecked() ? "1" : "0"));
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }

}
