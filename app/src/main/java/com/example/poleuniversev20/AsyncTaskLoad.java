package com.example.poleuniversev20;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;

public class AsyncTaskLoad extends AsyncTaskLoader {
    private String mURL;

    public AsyncTaskLoad(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<SportsmeDataHolder> loadInBackground() {

        ArrayList<SportsmeDataHolder> data_holders = new QueryUtils().extractSportmsmens(mURL);

        return data_holders;
    }
}
