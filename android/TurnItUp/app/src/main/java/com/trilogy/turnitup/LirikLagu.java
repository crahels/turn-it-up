package com.trilogy.turnitup;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;


public class LirikLagu extends AsyncTaskLoader<String> {

    private String mQueryString;

    public LirikLagu(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
        Log.d("LirikLagu","LirikLagu");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getLyricsInfo(mQueryString);
    }

}