package com.trilogy.turnitup;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class DaftarLagu extends Fragment implements SensorEventListener {

    public MusicPlayer act;
    View view;
    public static ArrayList<SingleSong> song;
    ListView musicList;
    FirebaseUser user;
    DatabaseReference myRef;
    public static CustomAdapter adapter;
    private static SensorManager sensorManager;
    private Sensor sensorLight;
    private Sensor sensorMagnetic;
    float bright;
    double magnet;
    SingleSong singleSong;
    String appendSong = "";
    TextView lrk;

    //shared preference
    boolean closer;
    boolean fourminutes;
    boolean shapeofyou;
    boolean play;
    boolean check_fourminutes;
    boolean check_shapeofyou;

    FloatingActionButton fab;

    private SharedPreference sharedPref;
    Activity context = this.getActivity();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("why",Boolean.toString(closer));
        act = (MusicPlayer) getActivity();
        //closer = act.unlockCloser;
        //act.mediaPlayer.release();

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        sharedPref = new SharedPreference();
        closer = sharedPref.getCloser(getContext());
        fourminutes = sharedPref.getFourminutes(getContext());
        shapeofyou = sharedPref.getShapeofyou(getContext());
        check_fourminutes = sharedPref.getFourminutes(getContext());
        check_shapeofyou = sharedPref.getShapeofyou(getContext());

        sensorManager = (SensorManager) getContext().getSystemService(Service.SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.daftar_lagu, container, false);
        lrk = (TextView) act.findViewById(R.id.lirik);
        musicList = (ListView) view.findViewById(R.id.music_list);

        Log.d("Laguuu", String.valueOf(closer));
        Log.d("Laguuu", String.valueOf(fourminutes));
        Log.d("Laguuu", String.valueOf(shapeofyou));

        song = new ArrayList<>();
        song.clear();
        if (closer) {
            song.add(new SingleSong("Shake It Off", "Taylor Swift", R.drawable.lock, R.raw.closer));
        } else {
            song.add(new SingleSong("Shake It Off", "Taylor Swift", R.drawable.closer, R.raw.closer));
        }
        if (fourminutes) {
            song.add(new SingleSong("Let It Go", "Demi Lovato", R.drawable.lock, R.raw.fourminutes));
        } else {
            song.add(new SingleSong("Let It Go", "Demi Lovato", R.drawable.fourminutes, R.raw.fourminutes));
        }
        if (shapeofyou) {
            song.add(new SingleSong("Timber", "Pitbull", R.drawable.lock, R.raw.shapeofyou));
        } else {
            song.add(new SingleSong("Timber", "Pitbull", R.drawable.shapeofyou, R.raw.shapeofyou));
        }

        adapter = new CustomAdapter(song, getContext());
        musicList.setAdapter(adapter);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.startIntentService();
                //changeListView();
            }
        });

        Log.d("Laguuu1", String.valueOf(closer));
        Log.d("Laguuu1", String.valueOf(fourminutes));
        Log.d("Laguuu1", String.valueOf(shapeofyou));

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                singleSong = song.get(position);
                if (singleSong.getTitle().equals("Shake It Off")) {
                    closer = sharedPref.getCloser(getContext());
                    play = closer;
                } else if (singleSong.getTitle().equals("Let It Go")) {
                    play = fourminutes;
                } else if (singleSong.getTitle().equals("Timber")) {
                    play = shapeofyou;
                }
                Log.d("aaa", String.valueOf(play));
                if (!play) {
                    act.mediaPlayer = MediaPlayer.create(act.getApplicationContext(), singleSong.getMusic());
                    act.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    act.songSelect(singleSong.getTitle(), singleSong.getSinger(), act.mediaPlayer.getDuration());
                    act.playMusic();
                    act.mediaPlayer.start();

                    if (position == 0) {
                        appendSong = "Taylor%20Swift/Shake%20It%20Off";
                    } else if (position == 1) {
                        appendSong = "Demi%20Lovato/Let%20It%20Go";
                    } else if (position == 2) {
                        appendSong = "Pitbull/Timber";
                    }
                    setViewLayout(R.layout.lirik_lagu);

                    Intent intent = new Intent(getActivity(), LyricContainer.class);
                    intent.putExtra("lirik_intent", appendSong);
                    startActivityForResult(intent, 1);
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (check_fourminutes) {
            sensorManager.unregisterListener(this, sensorMagnetic);
        }
        if (check_shapeofyou) {
            sensorManager.unregisterListener(this, sensorLight);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (check_fourminutes) {
            sensorManager.unregisterListener(this, sensorMagnetic);
        }
        if (check_shapeofyou) {
            sensorManager.unregisterListener(this, sensorLight);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPref.getShapeofyou(getContext())) {
            sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorMagnetic != null) {
            if (sharedPref.getFourminutes(getContext())) {
                sensorManager.registerListener(this, sensorMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.magnetic), Toast.LENGTH_SHORT).show();
            context.finish();
        }
    }

   @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float azimuth = Math.round(sensorEvent.values[0]);
            float pitch = Math.round(sensorEvent.values[1]);
            float roll = Math.round(sensorEvent.values[2]);

            magnet = Math.sqrt((azimuth * azimuth) + (pitch * pitch) + (roll * roll));

            if (magnet > 30) {
                fourminutes = false;
                sharedPref.setFourminutes(this.getActivity(),fourminutes);
                myRef.child("users").child(user.getUid()).child("lockedSong").child("1").setValue(fourminutes);
                sensorManager.unregisterListener(this, sensorMagnetic);
            }
            Log.d("magnet", Double.toString(magnet));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            bright = sensorEvent.values[0];
            //Toast.makeText(getContext(), Float.toString(bright), Toast.LENGTH_SHORT).show();
            Log.d("light", Float.toString(bright));
            if (bright > 100) {
                shapeofyou = false;
                sharedPref.setShapeofyou(this.getActivity(),shapeofyou);
                myRef.child("users").child(user.getUid()).child("lockedSong").child("2").setValue(shapeofyou);
                sensorManager.unregisterListener(this, sensorLight);
            }
        }

        changeListView();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class SingleSong {
        String title;
        String singer;
        int cover;
        int music;

        public SingleSong(String title, String singer, int cover, int music) {
            this.title = title;
            this.singer = singer;
            this.cover = cover;
            this.music = music;
        }

        public String getTitle() {
            return title;
        }

        public String getSinger() {
            return singer;
        }

        public int getCover() {
            return cover;
        }

        public int getMusic() {
            return music;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public void setCover(int cover) {
            this.cover = cover;
        }
    }

    public class CustomAdapter extends ArrayAdapter<SingleSong> implements View.OnClickListener {
        private ArrayList<SingleSong> list;
        Context c;

        private class ViewHolder {
            TextView title;
            TextView singer;
            ImageView cover;
        }

        public CustomAdapter(ArrayList<SingleSong> song, Context con) {
            super(con, R.layout.custom_music_list, song);
            this.list = song;
            this.c = con;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SingleSong song = getItem(i);

            ViewHolder viewHolder;

            if (view == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.custom_music_list, viewGroup, false);
                viewHolder.title = (TextView) view.findViewById(R.id.textView_title);
                viewHolder.singer = (TextView) view.findViewById(R.id.textView_singer);
                viewHolder.cover = (ImageView) view.findViewById(R.id.cover);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.title.setText(song.getTitle());
            viewHolder.singer.setText(song.getSinger());
            viewHolder.cover.setImageResource(song.getCover());

            return view;

        }

        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            Object object = getItem(position);
            SingleSong song = (SingleSong) object;
        }
    }

    private void setViewLayout(int id) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    public void changeListView() {
        closer = sharedPref.getCloser(getContext());
        if (!closer) {
            song.get(0).setCover(R.drawable.closer);
        } else {
            song.get(0).setCover(R.drawable.lock);
        }

        if (!fourminutes) {
            song.get(1).setCover(R.drawable.fourminutes);
        } else {
            song.get(1).setCover(R.drawable.lock);
        }

        if (!shapeofyou) {
            song.get(2).setCover(R.drawable.shapeofyou);
        } else {
            song.get(2).setCover(R.drawable.lock);
        }

        adapter = new CustomAdapter(song, getContext());
        musicList.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra("ini_lirik");
                act.songLyric(reply);
            }
        }
    }
}
