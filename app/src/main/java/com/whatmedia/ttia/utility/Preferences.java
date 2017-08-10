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
    private final static String TAG_MY_FLIGHT_INFO = "my_flight";

    private static SharedPreferences preferences;

    /**
     * Save Alert clock notify data
     *
     * @param context
     * @param datas
     */
    public static void saveClockData(Context context, String datas) {
        Log.d(TAG, datas);
        preferences = context.getSharedPreferences(TAG_CLOCK_DATA, 0);
        preferences.edit()
                .putString(TAG_CLOCK_DATA, datas)
                .commit();
    }

    /**
     * Get Alert clock notify data
     *
     * @param context
     * @return
     */
    public static String getClockData(Context context) {
        preferences = context.getSharedPreferences(TAG_CLOCK_DATA, 0);
        return preferences.getString(TAG_CLOCK_DATA, "");
    }

    /**
     * Save My flights data
     *
     * @param context
     * @param datas
     */
    public static void saveMyFlightsData(Context context, String datas) {
        Log.d(TAG, datas);
        preferences = context.getSharedPreferences(TAG_MY_FLIGHT_INFO, 0);
        preferences.edit()
                .putString(TAG_MY_FLIGHT_INFO, datas)
                .commit();
    }

    /**
     * Get My flights data
     *
     * @param context
     * @return
     */
    public static String getMyFlightsDat(Context context) {
        preferences = context.getSharedPreferences(TAG_MY_FLIGHT_INFO, 0);
        return preferences.getString(TAG_MY_FLIGHT_INFO, "");
    }


}
