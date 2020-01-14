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

public class RangingArtistic extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;
    TextView category;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingArtistic.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Artistic");
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_artistic);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sportsmenName = getIntent().getStringExtra("SportsmenName");
        sportsmenCategory = getIntent().getStringExtra("Сategory");
        TextView nameView = findViewById(R.id.ChoreographySportsmenName);
        nameView.setText(sportsmenName);
        buttonLoad = findViewById(R.id.rangingLoadChoreography);
        TextView category = findViewById(R.id.formCategoryArtsic);
        category.setText(sportsmenCategory);

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostRanging.class);
                try {

                    //Scores, action, category
                    intent.putExtra("scores", (Serializable) GetScore());
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

    private Map<String, String> GetScore() {
        //Scores
        RadioGroup radioGroupPower = findViewById(R.id.radioArtisticEmotions);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioArtisticSuit);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioArtisticCharacter);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioArtisticIdea);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioArtisticStage);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.editArtisticComment);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "Artistic");
        score.put("name", sportsmenName);
        score.put("judge", judgeName);
        score.put("emotions", (String) radioButtonPower.getText());
        score.put("suit", (String) radioButtonFlex.getText());
        score.put("character", (String) radioButtonCombination.getText());
        score.put("idea", (String) radioButtonDoublePole.getText());
        score.put("stage", (String) radioButtonBalance.getText());
        score.put("comment", TechnikJudgeCommentView.getText().toString());
        return score;
    }


}
