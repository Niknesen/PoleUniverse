package com.example.poleuniversev20;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import java.util.ArrayList;

public class SportsmenList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SportsmeDataHolder>> {
    private ArrayList<SportsmeDataHolder> sportsmens;
    private String judge;
    public static String category, action;
    private ListView sportsmenListView;
    private SpecifikCategoryAdapter mDataAdapeter;
    private TextView SpecifikCategoryName;
    private static final String LOG_TAG = SportsmenList.class.getName();
    private String mUrlAdress;
    private String mDay;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specifik_category);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        category = intent.getExtras().getString("Category");
        action = intent.getExtras().getString("Action");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mDay = sharedPref.getString("Day", "No Day");
        if (mDay.equals("1"))
            mUrlAdress = "https://script.google.com/macros/s/AKfycbz4lGtSLaDa8oKYMRSpErdx1csUSPu3mJUAmAY1K2EpYSd36fo/exec";
        else
            mUrlAdress = "https://script.google.com/macros/s/AKfycbzg0ZAq2c3HHbcuev8-2svM-lLMJwnuAArKV6Gq/exec";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        judge = preferences.getString("JudgeNameChoreography", "No Judge");
        this.setTitle(action + ". Судья: " + judge);
        SpecifikCategoryName = findViewById(R.id.SpecifikCategoryName);
        SpecifikCategoryName.setText(category);
        Toast toast = Toast.makeText(getApplicationContext(), category, Toast.LENGTH_SHORT);
        toast.show();
        sportsmenListView = findViewById(R.id.listViewSpecifikCategory);

        if (sportsmens != null) {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
            sportsmenListView.setAdapter(new SpecifikCategoryAdapter(this, sportsmens, R.layout.category_listview_fragment));
        } else {
            sportsmens = new ArrayList<SportsmeDataHolder>();
            mDataAdapeter = new SpecifikCategoryAdapter(this, sportsmens, R.layout.category_listview_fragment);
            getLoaderManager().initLoader(1, null, this);
            ListView listView = findViewById(R.id.listViewSpecifikCategory);
            listView.setAdapter(mDataAdapeter);
        }

    }

    @Override
    public Loader<ArrayList<SportsmeDataHolder>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(mUrlAdress);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("category", category);
        return new AsyncTaskLoad(SportsmenList.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<SportsmeDataHolder>> loader, ArrayList<SportsmeDataHolder> sportsmeDataHolders) {
        mDataAdapeter.clear();

        if (sportsmeDataHolders != null && !sportsmeDataHolders.isEmpty()) {
            sportsmens = new ArrayList<SportsmeDataHolder>(sportsmeDataHolders);
            mDataAdapeter.addAll(sportsmeDataHolders);
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }

        //Check connectivity status by
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean isConected = netInfo != null && netInfo.isConnectedOrConnecting();
        View textView = (TextView) findViewById(R.id.emptyview);
        sportsmenListView.setEmptyView(textView);
        if (isConected) ((TextView) textView).setText("No Data");
        else ((TextView) textView).setText("NO internet connection");
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<SportsmeDataHolder>> loader) {
        mDataAdapeter.clear();
    }

    public static String getCategory() {
        return category;

    }

    public static String getAction() {
        return action;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SportsmenList.this);
        String day = sharedPref.getString("Day", "No Day");

        if (day.equals("1")) {
            Intent intent = new Intent(SportsmenList.this, Categories1Day.class);
            intent.putExtra("Category", category);
            startActivity(intent);
            return true;
        } else {
            Intent intent = new Intent(SportsmenList.this, Categories2Day.class);
            intent.putExtra("Category", category);
            startActivity(intent);
            return true;
        }
    }
}
