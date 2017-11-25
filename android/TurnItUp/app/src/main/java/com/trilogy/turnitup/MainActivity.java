package com.trilogy.turnitup;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    public static Intent svc;
    public static MediaPlayer mp;
    private SharedPreference sharedPreference;
    private ProgressBar spinner;
    private User user;

    private Button mHowtoplay;
    private Button mProfile;
    private Button mHighscore;
    private Button mSelect;
    private Button mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHowtoplay = (Button) findViewById(R.id.button_howtoplay);
        mProfile = (Button) findViewById(R.id.button_profile);
        mHighscore = (Button) findViewById(R.id.button_highscore);
        mSelect = (Button) findViewById(R.id.button_select);
        mHelp = (Button) findViewById(R.id.button_help);
        mHowtoplay.setEnabled(false);
        mProfile.setEnabled(false);
        mHighscore.setEnabled(false);
        mSelect.setEnabled(false);
        mHelp.setEnabled(false);

        spinner = (ProgressBar) findViewById(R.id.progressBar);

        spinner.setVisibility(View.VISIBLE);

        svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
        mp = MediaPlayer.create(this, R.raw.button_sound);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreference = new SharedPreference();
        retrieveDatabase();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.menu_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }
    }

    private void retrieveDatabase() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                setSharedPreference(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void setSharedPreference(User user) {
        sharedPreference.setCloser(this, user.getLockedSong().get(0));
        sharedPreference.setFourminutes(this, user.getLockedSong().get(1));
        sharedPreference.setShapeofyou(this, user.getLockedSong().get(2));
        spinner.setVisibility(View.GONE);
        mHowtoplay.setEnabled(true);
        mProfile.setEnabled(true);
        mHighscore.setEnabled(true);
        mSelect.setEnabled(true);
        mHelp.setEnabled(true);
        //Log.d("sharedpref", mUser.getUid() + " " + user.getLockedSong().get(0) + " " + user.getLockedSong().get(1) + " " + user.getLockedSong().get(2));
    }

    public void showProfile(View view) {
        mp.start();
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void showSelect(View view) {
        mp.start();
        stopService(svc);
        //setSharedPreference(user);
        Intent intent = new Intent(this, MusicPlayer.class);
        startActivity(intent);
    }

    public void showHighscore(View view) {
        mp.start();
        Intent intent = new Intent(this, SongActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (svc != null) {
            startService(svc);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (svc != null) {
            stopService(svc);
        }
    }

    public void showHowtoPlay(View view) {
        mp.start();
        Intent intent = new Intent(this, HowtoplayActivity.class);
        startActivity(intent);
    }

    public void showHelp(View view) {
        mp.start();
        stopService(svc);
        // temporary
        String url = "https://crahels.github.io/TurnItUp/";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("TurnItUp", "Can't handle this!");
        }
    }
}
