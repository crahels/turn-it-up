package com.trilogy.turnitup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.color.darker_gray;

public class HowtoplayActivity extends Activity {
    private ImageView arrow_left, arrow_up, arrow_down, arrow_right, arrow_left2, arrow_right2, arrow_up2, arrow_down2;
    private ImageView control;
    private TextView textBegin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtoplay);

        arrow_left = findViewById(R.id.left_arrow);
        arrow_up = findViewById(R.id.up_arrow);
        arrow_down = findViewById(R.id.down_arrow);
        arrow_right = findViewById(R.id.right_arrow);
        control = findViewById(R.id.controller);
        textBegin = findViewById(R.id.text_begin);
        arrow_left2 = findViewById(R.id.left_arrow2);
        arrow_right2 = findViewById(R.id.right_arrow2);
        arrow_up2 = findViewById(R.id.up_arrow2);
        arrow_down2 = findViewById(R.id.down_arrow2);

        textBegin.getBackground().setAlpha(200);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setTextSize(80);
                textBegin.setTextColor(getResources().getColor(R.color.colorSelect));
                textBegin.setText(getResources().getString(R.string.ready));
            }
        }, 5000);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setText(getResources().getString(R.string.go));
            }
        }, 6000);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setVisibility(View.INVISIBLE);
                textBegin.setBackgroundColor(0);
            }
        }, 7000);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setVisibility(View.VISIBLE);
                textBegin.setTextColor(Color.parseColor("#16a962"));
                textBegin.setText(getResources().getString(R.string.perfect));
            }
        }, 9100);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setTextColor(Color.parseColor("#1d68d9"));
                textBegin.setText(getResources().getString(R.string.good));
            }
        }, 19100);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setTextColor(Color.parseColor("#16a962"));
                textBegin.setText(getResources().getString(R.string.perfect));
            }
        }, 21600);

        textBegin.postDelayed(new Runnable() {
            @Override
            public void run() {
                textBegin.setVisibility(View.VISIBLE);
                textBegin.setBackgroundColor(Color.parseColor("#FFFFFF"));
                textBegin.getBackground().setAlpha(200);
                textBegin.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textBegin.setText(getResources().getString(R.string.awesome));
            }
        }, 22000);

        control.postDelayed(new Runnable() {
            @Override
            public void run() {
                control.setImageResource(R.drawable.left_press);
            }
        }, 9000);

        control.postDelayed(new Runnable() {
            @Override
            public void run() {
                control.setImageResource(R.drawable.right_press);
            }
        }, 11500);

        control.postDelayed(new Runnable() {
            @Override
            public void run() {
                control.setImageResource(R.drawable.up_press);
            }
        }, 14000);

        control.postDelayed(new Runnable() {
            @Override
            public void run() {
                control.setImageResource(R.drawable.down_press);
            }
        }, 16500);

        control.postDelayed(new Runnable() {
            @Override
            public void run() {
                control.setImageResource(R.drawable.left_right_press);
            }
        }, 19000);

        control.postDelayed(new Runnable() {
            @Override
            public void run() {
                control.setImageResource(R.drawable.up_down_press);
            }
        }, 21500);

        moveTransition();
    }

    public void moveTransition() {
        Animation anim_left = new AlphaAnimation(0.0f, 1.0f);
        anim_left.setDuration(500);
        Animation trAnimation_left = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, -1010);
        trAnimation_left.setDuration(2000);

        AnimationSet set_left = new AnimationSet(true);
        set_left.addAnimation(anim_left);
        set_left.addAnimation(trAnimation_left);
        set_left.setStartOffset(7000);
        arrow_left.startAnimation(set_left);

        Animation anim_right = new AlphaAnimation(0.0f, 1.0f);
        anim_right.setDuration(500);
        Animation trAnimation_right = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, -1010);
        trAnimation_right.setDuration(2000);

        AnimationSet set_right = new AnimationSet(true);
        set_right.addAnimation(anim_right);
        set_right.addAnimation(trAnimation_right);
        set_right.setStartOffset(9500);
        arrow_right.startAnimation(set_right);

        Animation anim_up = new AlphaAnimation(0.0f, 1.0f);
        anim_up.setDuration(500);
        Animation trAnimation_up = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, -1010);
        trAnimation_up.setDuration(2000);

        AnimationSet set_up = new AnimationSet(true);
        set_up.addAnimation(anim_up);
        set_up.addAnimation(trAnimation_up);
        set_up.setStartOffset(12000);
        arrow_up.startAnimation(set_up);

        Animation anim_down = new AlphaAnimation(0.0f, 1.0f);
        anim_down.setDuration(500);
        Animation trAnimation_down = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, -1010);
        trAnimation_down.setDuration(2000);

        AnimationSet set_down = new AnimationSet(true);
        set_down.addAnimation(anim_down);
        set_down.addAnimation(trAnimation_down);
        set_down.setStartOffset(14500);
        arrow_down.startAnimation(set_down);


        Animation anim_left_right = new AlphaAnimation(0.0f, 1.0f);
        anim_left_right.setDuration(500);
        Animation trAnimation_left_right = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, -1010);
        trAnimation_left_right.setDuration(2000);

        AnimationSet set_left_right = new AnimationSet(true);
        set_left_right.addAnimation(anim_left_right);
        set_left_right.addAnimation(trAnimation_left_right);
        set_left_right.setStartOffset(17000);
        arrow_left2.startAnimation(set_left_right);
        arrow_right2.startAnimation(set_left_right);

        Animation anim_up_down = new AlphaAnimation(0.0f, 1.0f);
        anim_up_down.setDuration(500);
        Animation trAnimation_up_down = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, -1010);
        trAnimation_up_down.setDuration(2000);

        AnimationSet set_up_down = new AnimationSet(true);
        set_up_down.addAnimation(anim_up_down);
        set_up_down.addAnimation(trAnimation_up_down);
        set_up_down.setStartOffset(19500);
        arrow_up2.startAnimation(set_up_down);
        arrow_down2.startAnimation(set_up_down);
    }
 }