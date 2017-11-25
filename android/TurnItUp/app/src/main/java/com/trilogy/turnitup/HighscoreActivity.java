package com.trilogy.turnitup;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private String currentSong;
    private static final String LOG_TAG = HighscoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_song);
        Intent intent = getIntent();
        currentSong = intent.getStringExtra("songtitle");
        takeUser();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.score_song_act);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayout.setBackground(getResources().getDrawable(R.drawable.back_landscape));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayout.setBackground(getResources().getDrawable(R.drawable.back_portrait));
        }
    }

    public void showOnScreen(ArrayList<UserID> mArrayUid, ArrayList<User> mArray) {
        ArrayAdapter adapter = new HighscoreAdapter(currentSong, mArrayUid, mArray, getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.list_score_song);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void takeUser() {
        myRef = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = myRef.child("users");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> arrayUser = new ArrayList<>();
                ArrayList<UserID> arrayUid = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    arrayUid.add(new UserID(postSnapshot.getKey(), Integer.valueOf(user.getHighscore(currentSong))));
                    arrayUser.add(user);
                }
                showOnScreen(arrayUid, arrayUser);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(LOG_TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
