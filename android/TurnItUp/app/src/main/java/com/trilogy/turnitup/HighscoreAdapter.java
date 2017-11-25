package com.trilogy.turnitup;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighscoreAdapter extends ArrayAdapter<User> implements View.OnClickListener{
    private ArrayList<User> user;
    private ArrayList<User> userTemp = new ArrayList<>();
    private ArrayList<UserID> UidTemp = new ArrayList<>();
    private String currentSong;
    private ArrayList<UserID> Uid;
    StorageReference mStorage;
    Context mContext;

    public HighscoreAdapter(String currentSong, ArrayList<UserID> mArrayUid, ArrayList<User> user, Context context) {
        super(context, R.layout.list_item, user);
        this.currentSong = currentSong;
        this.user = user;
        this.Uid = mArrayUid;
        this.mContext = context;
        mStorage = FirebaseStorage.getInstance().getReference();
        sortUidGetTen();
        sortUserGetTen();
    }

    public void sortUidGetTen() {
        Collections.sort(Uid, new Comparator<UserID>() {
            @Override
            public int compare(UserID u1, UserID u2) {
                return u2.getScore() - u1.getScore();
            }
        });
        if (Uid.size() >= 10) {
            UidTemp.addAll(Uid.subList(0, 10));
            Uid.clear();
            Uid.addAll(UidTemp.subList(0, UidTemp.size()));
        } else {
        }
    }

    public void sortUserGetTen() {
        Collections.sort(user, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u2.getHighscore(currentSong) - u1.getHighscore(currentSong);
            }
        });
        if (user.size() >= 10) {
            userTemp.addAll(user.subList(0, 10));
            user.clear();
            user.addAll(userTemp.subList(0, userTemp.size()));
        } else {
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        final ImageView image = rowView.findViewById(R.id.image_score);
        if (position % 2 == 0) {
            //image.setBackground(rowView.getResources().getDrawable(R.mipmap.ic_score1));
            rowView.setBackgroundColor(Color.parseColor("#fb3742"));
        } else {
            //image.setBackground(rowView.getResources().getDrawable(R.mipmap.ic_score2));
            rowView.setBackgroundColor(Color.parseColor("#54c3de"));
        }

        rowView.getBackground().setAlpha(100);

        try {
            final File localFile = File.createTempFile("images", "jpg");
            mStorage.child("pp" + Uid.get(position).getUid() + ".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    image.setImageURI(Uri.fromFile(localFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView userDisplay = rowView.findViewById(R.id.display_user);
        TextView score = rowView.findViewById(R.id.display_score);
        userDisplay.setText(String.valueOf(user.get(position).getUsername()));
        score.setText(String.valueOf(user.get(position).getHighscore(currentSong)));

        ImageView imageStar = rowView.findViewById(R.id.display_star);
        int category = categoryScore(user.get(position).getHighscore(currentSong));
        if (category == 1) {
            imageStar.setImageResource(R.drawable.star1);
        } else if (category == 2) {
            imageStar.setImageResource(R.drawable.star2);
        } else if (category == 3) {
            imageStar.setImageResource(R.drawable.star3);
        } else if (category == 4) {
            imageStar.setImageResource(R.drawable.star4);
        } else {
            imageStar.setImageResource(R.drawable.star5);
        }
        return rowView;
    }

    public int categoryScore(int score) {
        if (score <= 200000) {
            return 1;
        } else if (score > 200000 && score <= 400000) {
            return 2;
        } else if (score > 400000 && score <= 600000) {
            return 3;
        } else if (score > 600000 && score <= 800000) {
            return 4;
        } else {
            return 5;
        }
    }

    @Override
    public void onClick(View view) {
    }
}
