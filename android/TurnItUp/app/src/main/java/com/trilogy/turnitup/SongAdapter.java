package com.trilogy.turnitup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<String> implements View.OnClickListener{
    private ArrayList<String> songs;
    Context mContext;

    public SongAdapter(ArrayList<String> songs, Context context) {
        super(context, R.layout.list_item, songs);
        this.songs = songs;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_song, parent, false);
        ImageView image = rowView.findViewById(R.id.image_song);

        if (position % 2 == 0) {
            image.setBackground(rowView.getResources().getDrawable(R.mipmap.ic_song1));
            rowView.setBackgroundColor(Color.parseColor("#fb3742"));
        } else {
            image.setBackground(rowView.getResources().getDrawable(R.mipmap.ic_song2));
            rowView.setBackgroundColor(Color.parseColor("#54c3de"));
        }
        rowView.getBackground().setAlpha(100);

        TextView textView = rowView.findViewById(R.id.text_song);
        textView.setText(String.valueOf(songs.get(position)));
        return rowView;
    }

    @Override
    public void onClick(View view) {
        String currentSong = songs.get((Integer) view.getTag());
        Intent detailIntent = new Intent(mContext, HighscoreActivity.class);
        detailIntent.putExtra("songtitle", currentSong);
        mContext.startActivity(detailIntent);
    }
}

