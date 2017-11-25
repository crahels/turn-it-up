package com.trilogy.turnitup;

/**
 * Created by User on 29/09/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class LyricContainer extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private String lirik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyricontainer);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Intent intent = getIntent();
        String query = intent.getStringExtra("lirik_intent");

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        Log.d("oncreateLC","oncreateLC");
        searchLyric(query);

    }

    public void searchLyric(String appendSong) {
        String queryString = appendSong;
        Log.d("querystring", queryString);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        } else {
            if (queryString.length() == 0) {
                lirik = "";
            } else {
                lirik = "No network connection";
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new LirikLagu(this, args.getString("queryString"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            boolean error = !jsonObject.getString("err").equals("none");
            if (error) {
                lirik = "Lyrics Not Found";
            } else {
                String lyrics = jsonObject.getString("lyric");
                lirik = lyrics;
            }
        } catch (Exception e) {
            lirik = "No result";
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.putExtra("ini_lirik", lirik);
        setResult(RESULT_OK,intent);
        finish();

        Log.d("lyricsfetched", data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}

