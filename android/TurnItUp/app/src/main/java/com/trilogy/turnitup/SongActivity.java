package com.trilogy.turnitup;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SongActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private DatabaseReference myRef;
    private ArrayList<String> songs;
    public static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        mp = MediaPlayer.create(this, R.raw.score_song_button);
        Intent intent = getIntent();
        addToDatabase();
    }

    public void showOnScreen(ArrayList<String> mArray) {
        songs = mArray;
        ArrayAdapter adapter = new SongAdapter(mArray, getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.list_highscore);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mp.start();
                String currentSong = songs.get(position);
                Intent detailIntent = new Intent(getApplicationContext(),HighscoreActivity.class);
                detailIntent.putExtra("songtitle", currentSong);
                startActivity(detailIntent);
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.song_act);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayout.setBackground(getResources().getDrawable(R.drawable.back_landscape));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayout.setBackground(getResources().getDrawable(R.drawable.back_portrait));
        }
    }

    public void addToDatabase() {
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        Query mySongQuery = myRef.child("songs");
        mySongQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> songs = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String songtitle = postSnapshot.getValue().toString();
                    songs.add(songtitle);
                }
                showOnScreen(songs);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(LOG_TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
