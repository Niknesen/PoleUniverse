package com.example.poleuniversev20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.preference.PreferenceManager;
import java.util.ArrayList;

public class SpecifikCategoryAdapter extends ArrayAdapter<SportsmeDataHolder> {
    String sportsmenName, SportsmenCategory, action;

    private boolean ratedTechnique;
    private boolean ratedChoreography;
    private boolean ratedArtistic;
    private boolean ratedPenalty;

    public SpecifikCategoryAdapter(Context context, ArrayList<SportsmeDataHolder> dataArray, int resource) {
        super(context, 0, dataArray);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SportsmeDataHolder sportsmen = getItem(position);
        View customView = convertView;
        if (customView == null) {
            customView = LayoutInflater.from(getContext()).inflate(R.layout.category_listview_fragment, parent, false);
        }
        TextView name = customView.findViewById(R.id.SportsmenName);
        sportsmenName = sportsmen.getmName();
        SportsmenCategory = SportsmenList.getCategory();
        action = SportsmenList.getAction();
        name.setText(sportsmenName);
        TextView rated = customView.findViewById(R.id.SportsmenRated);

        rated.setTextColor(Color.parseColor("#A9A9A9"));
        if (action.equals("Technique") & sportsmen.isRatedTechnique()) {
            rated.setText(R.string.Checked);
            rated.setTextColor(Color.parseColor("#00ff00"));
        } else if (action.equals("Choreography") & sportsmen.isRatedChoreography()) {
            rated.setText(R.string.Checked);
            rated.setTextColor(Color.parseColor("#00ff00"));
        } else if (action.equals("Artistic") & sportsmen.isRatedArtistic()) {
            rated.setText(R.string.Checked);
            rated.setTextColor(Color.parseColor("#00ff00"));
        } else if (action.equals("Penalty") & sportsmen.isRatedPenalty()) {
            rated.setText(R.string.Checked);
            rated.setTextColor(Color.parseColor("#00ff00"));
        } else rated.setText(R.string.UnChecked);

        Log.v("Action", action + sportsmen.isRatedChoreography() + (action.equals("Choreography") & sportsmen.isRatedChoreography()));
        TextView positionN = customView.findViewById(R.id.SportsmenPosition);
        positionN.setText(position + 1 + "");

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sportsmenName = sportsmen.getmName();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                String day = sharedPref.getString("Day", "No Day");
                if (day.equals("1")) StartFirstDay();
                else StartSecondDay();
            }
        });
        return customView;
    }

    public void StartFirstDay() {
        Intent ranging = new Intent();
        if (action.equals("Technique")) ranging = new Intent(getContext(), RangingTechnik.class);
        else if (action.equals("Choreography"))
            ranging = new Intent(getContext(), RangingChoreography.class);
        else if (action.equals("Artistic"))
            ranging = new Intent(getContext(), RangingArtistic.class);
        else if (action.equals("Penalty")) ranging = new Intent(getContext(), RangingPenalty.class);
        ranging.putExtra("SportsmenName", sportsmenName);
        ranging.putExtra("Сategory", SportsmenCategory);
        getContext().startActivity(ranging);
    }


    public void StartSecondDay() {
        Intent ranging = new Intent();
        if (action.equals("Technique"))
            ranging = new Intent(getContext(), RangingTechnikDayTwo.class);
        else if (action.equals("Choreography"))
            ranging = new Intent(getContext(), RangingChoreographyDayTwo.class);
        else if (action.equals("Artistic"))
            ranging = new Intent(getContext(), RangingArtisticDayTwo.class);
        else if (action.equals("Penalty")) ranging = new Intent(getContext(), RangingPenalty.class);
        ranging.putExtra("SportsmenName", sportsmenName);
        Log.v("nametest2", sportsmenName);
        ranging.putExtra("Сategory", SportsmenCategory);
        getContext().startActivity(ranging);
    }
}
