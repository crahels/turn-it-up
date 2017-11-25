package com.trilogy.turnitup;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 29/09/2017.
 */

public class SharedPreference {
    public static final String songPreference = "song_preference";

    public static final String closer = "closer";
    public static final String fourminutes = "fourminutes";
    public static final String shapeofyou = "shapeofyou";

    public void setCloser(Context context, boolean status) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(songPreference, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(closer,status);

        editor.commit();
    }

    public boolean getCloser(Context context) {
        SharedPreferences sharedPref;
        boolean status;
        sharedPref = context.getSharedPreferences(songPreference, Context.MODE_PRIVATE);
        status = sharedPref.getBoolean(closer, Boolean.parseBoolean(null));
        return status;
    }

    public void setFourminutes(Context context, boolean status) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(songPreference, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(fourminutes,status);

        editor.commit();
    }

    public boolean getFourminutes(Context context) {
        SharedPreferences sharedPref;
        boolean status;
        sharedPref = context.getSharedPreferences(songPreference, Context.MODE_PRIVATE);
        status = sharedPref.getBoolean(fourminutes, Boolean.parseBoolean(null));
        return status;
    }

    public void setShapeofyou(Context context, boolean status) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = context.getSharedPreferences(songPreference, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean(shapeofyou,status);

        editor.commit();
    }

    public boolean getShapeofyou(Context context) {
        SharedPreferences sharedPref;
        boolean status;
        sharedPref = context.getSharedPreferences(songPreference, Context.MODE_PRIVATE);
        status = sharedPref.getBoolean(shapeofyou, Boolean.parseBoolean(null));
        return status;
    }
}
