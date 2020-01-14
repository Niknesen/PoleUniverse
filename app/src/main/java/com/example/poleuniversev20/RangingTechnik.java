package com.example.poleuniversev20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Parcelable;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RangingTechnik extends AppCompatActivity {
    Button buttonLoad;
    String sportsmenName, sportsmenCategory;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(RangingTechnik.this, SportsmenList.class);
        intent.putExtra("Category", sportsmenCategory);
        intent.putExtra("Action", "Technique");
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SportsmenName
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_technique);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        sportsmenName = getIntent().getStringExtra("SportsmenName");
        Log.v("TEST0, ", sportsmenName);
        sportsmenCategory = getIntent().getStringExtra("Сategory");

        TextView nameView = findViewById(R.id.TechniDancerName);
        nameView.setText(sportsmenName);
        buttonLoad = findViewById(R.id.buttonSaveRangingTechnik);
        TextView category = findViewById(R.id.formCategoryTechnik);
        category.setText(sportsmenCategory);
        //PostButton -> PostRanging.class,
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText textView = findViewById(R.id.TechnikJudgeComment);
                // textView.setCursorVisible(false);
                try {
                    Intent intent = new Intent(getApplicationContext(), PostRanging.class);
                    intent.putExtra("scores", (Serializable) GetScore()); //Put your id to your next Intent
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

    private Map<String, String> GetScore() {
        //Scores
        Log.d("KEKE", sportsmenName + "11111");
        RadioGroup radioGroupPower = findViewById(R.id.radioTechnikPower);
        RadioButton radioButtonPower = findViewById(radioGroupPower.getCheckedRadioButtonId());

        RadioGroup radioGroupFlex = findViewById(R.id.radioTechnikFlex);
        RadioButton radioButtonFlex = findViewById(radioGroupFlex.getCheckedRadioButtonId());

        RadioGroup radioGroupCombination = findViewById(R.id.radioTechnikCombination);
        RadioButton radioButtonCombination = findViewById(radioGroupCombination.getCheckedRadioButtonId());

        RadioGroup radioGroupDoublePole = findViewById(R.id.radioTechnikDoublePole);
        RadioButton radioButtonDoublePole = findViewById(radioGroupDoublePole.getCheckedRadioButtonId());

        RadioGroup radioGroupBalance = findViewById(R.id.radioTechnikBalance);
        RadioButton radioButtonBalance = findViewById(radioGroupBalance.getCheckedRadioButtonId());

        RadioGroup radioGroupDynamic = findViewById(R.id.radioTechnikDynamic);
        RadioButton radioButtonDynamic = findViewById(radioGroupDynamic.getCheckedRadioButtonId());
        //Name of PoleDancer
        TextView TechniDancerNameView = findViewById(R.id.TechniDancerName);

        //Judge Comments
        EditText TechnikJudgeCommentView = findViewById(R.id.TechnikJudgeComment);

        //Judge Name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String judgeName = preferences.getString("JudgeNameChoreography", "No Judge");

        //

        Map<String, String> score = new HashMap<>();
        score.put("category", sportsmenCategory);
        score.put("action", "Technik");
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