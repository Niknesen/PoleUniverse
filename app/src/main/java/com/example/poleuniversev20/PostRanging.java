package com.example.poleuniversev20;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;

public class PostRanging extends AppCompatActivity {
    private String mUrl;
    private String mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting_data);
        addItemToSheet();
        Log.d("KO", mDay);


    }


    private void addItemToSheet() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mDay = sharedPref.getString("Day", "No Day");
        if (mDay.equals("1"))
            mUrl = "https://script.google.com/macros/s/AKfycbz4lGtSLaDa8oKYMRSpErdx1csUSPu3mJUAmAY1K2EpYSd36fo/exec";
        else
            mUrl = "https://script.google.com/macros/s/AKfycby65Vdmb7URkGuTnKyGS3OZzw-uDpLLYTvL08G74YeVaj0ZTlM/exec";
        final ProgressDialog loading = ProgressDialog.show(this, "Оценка принята", "Загружаем...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response ", response);
                        loading.dismiss();

                        Toast.makeText(PostRanging.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PostRanging.this, SportsmenList.class);
                        intent.putExtra("Category", getIntent().getStringExtra("Category"));
                        intent.putExtra("Action", getIntent().getStringExtra("Action"));
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Intent intent = getIntent();
                Map<String, String> scores = new HashMap<String, String>((HashMap<String, String>) intent.getSerializableExtra("scores"));
                Log.d("Post", scores.size() + "");
                return scores;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }
}
