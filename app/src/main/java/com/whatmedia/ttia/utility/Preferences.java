package com.whatmedia.ttia.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class Preferences {
    private final static String TAG = Preferences.class.getSimpleName();

    private final static String TAG_CLOCK_DATA = "clock";

    private static SharedPreferences preferences;

    public static void saveClockData(Context context, String datas) {
        Log.d(TAG, datas);
        preferences = context.getSharedPreferences(TAG_CLOCK_DATA, 0);
        preferences.edit()
                .putString(TAG_CLOCK_DATA, datas)
                .commit();
    }

    public static String getClockData(Context context) {
        preferences = context.getSharedPreferences(TAG_CLOCK_DATA, 0);
        return preferences.getString(TAG_CLOCK_DATA, "");
    }
}
