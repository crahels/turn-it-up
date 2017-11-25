package com.trilogy.turnitup;

import android.*;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import static java.security.AccessController.getContext;


public class MusicPlayer extends AppCompatActivity{
    DatabaseReference myRef;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Handler handler;
    Runnable runnable;
    TextView lrk;
    FirebaseUser user;
    SharedPreference sharedPref = new SharedPreference();

    //Location
    boolean unlockCloser;
    private static final String TAG = "Cari Lokasi";

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private String mAddressOutput;
    private AddressResultReceiver mResultReceiver;
    private Boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;
    private Boolean Location=true;
    //Location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Location = sharedPref.getCloser(this);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Location
        if (Location) {
            mResultReceiver = new AddressResultReceiver(new Handler());

            mRequestingLocationUpdates = false;
            mLastUpdateTime = "";

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mSettingsClient = LocationServices.getSettingsClient(this);

            createLocationCallback();
            createLocationRequest();
            buildLocationSettingsRequest();
            if (!mRequestingLocationUpdates) {
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            }
        }
        //Location

        lrk = (TextView) findViewById(R.id.lirik);

        handler = new Handler();

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.init);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        seekBar.setMax(0);
        playMusic();
        mediaPlayer.start();
    }

    public  void  playMusic() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playMusic();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        if (Location) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }

    public void songSelect(String title, String singer, int duration) {
        TextView t = (TextView) findViewById(R.id.song_title);
        t.setText(title);

        TextView s = (TextView) findViewById(R.id.singer);
        s.setText(singer);

        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setMax(duration);
    }

    public void songLyric(String lirik) {
        TextView t = (TextView) findViewById(R.id.lirik);
        t.setText(lirik);
    }

    //Location
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            }
        };
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MusicPlayer.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(MusicPlayer.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }
                    }
                });
    }

    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                    }
                });
    }

    public void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
        startService(intent);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();
            if (resultCode == Constants.SUCCESS_RESULT) {
                checkLocation();
            }

        }

    }

    private void displayAddressOutput() {
        Log.d("alamaaaat",mAddressOutput);
    }

    private void checkLocation() {
        if (mAddressOutput.contains("Jl. I")) {
            showToast("A song had been unlocked");
            unlockCloser = false;
            sharedPref.setCloser(this, unlockCloser);
            myRef.child("users").child(user.getUid()).child("lockedSong").child("0").setValue(unlockCloser);
            Log.d("ada apa", Boolean.toString(unlockCloser));
            //coba-coba
            DaftarLagu.song.get(0).setCover(R.drawable.closer);
            DaftarLagu.adapter.notifyDataSetChanged();
            //coba-coba
            //getFragmentManager().findFragmentById(R.id.music_list);
            stopLocationUpdates();
        } else {
            Toast.makeText(this, "Go to Labtek 101!", Toast.LENGTH_SHORT).show();
        }
    }
}
