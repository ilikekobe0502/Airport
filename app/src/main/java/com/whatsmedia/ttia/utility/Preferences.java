package com.whatsmedia.ttia.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.whatsmedia.ttia.enums.LanguageSetting;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class Preferences {
    private final static String TAG = Preferences.class.getSimpleName();

    public final static String TAG_ERROR = "error";
    private final static String TAG_CLOCK_DATA = "clock";
    private final static String TAG_MY_FLIGHT_INFO = "my_flight";
    private final static String TAG_LOCALE_Setting = "locale";
    private final static String TAG_FCM_TOKEN = "fcm_token";
    private final static String TAG_FIRST_INIT = "first";
    private final static String TAG_SCREEN_MODE = "screen_mode";
    private final static String TAG_LANGUAGE_LIST = "language_list";
    private final static String TAG_MY_FLIGHTS_FIRST = "my_flights_first";
    private final static String TAG_RESTORE = "restore";

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
    public static String getMyFlightsData(Context context) {
        preferences = context.getSharedPreferences(TAG_MY_FLIGHT_INFO, 0);
        return preferences.getString(TAG_MY_FLIGHT_INFO, "");
    }

    /**
     * Save Locale Setting
     *
     * @param context
     * @param locale
     */
    public static void saveLocaleSetting(Context context, String locale) {
        Log.d(TAG, locale);
        preferences = context.getSharedPreferences(TAG_LOCALE_Setting, 0);
        preferences.edit()
                .putString(TAG_LOCALE_Setting, locale)
                .commit();
    }

    /**
     * Get My flights data
     *
     * @param context
     * @return
     */
    public static String getLocaleSetting(Context context) {
        preferences = context.getSharedPreferences(TAG_LOCALE_Setting, 0);
        return preferences.getString(TAG_LOCALE_Setting, LanguageSetting.TAG_TRADITIONAL_CHINESE.getLocale().toString());
    }

    /**
     * Save fcm token
     *
     * @param context
     * @param token
     */
    public static void saveFCMToken(Context context, String token) {
        Log.d(TAG, "FCM token = " + token);

        preferences = context.getSharedPreferences(TAG_FCM_TOKEN, 0);
        preferences.edit()
                .putString(TAG_FCM_TOKEN, token)
                .commit();
    }

    /**
     * Get fcm token
     *
     * @param context
     * @return
     */
    public static String getFCMToken(Context context) {
        preferences = context.getSharedPreferences(TAG_FCM_TOKEN, 0);
        return preferences.getString(TAG_FCM_TOKEN, "");
    }

    /**
     * Save user first init
     *
     * @param context
     * @param first
     */
    public static void saveUserFirstInit(Context context, boolean first) {
        preferences = context.getSharedPreferences(TAG_FIRST_INIT, 0);
        preferences.edit()
                .putBoolean(TAG_FIRST_INIT, first)
                .commit();
    }

    /**
     * Get user first init
     *
     * @param context
     * @return
     */
    public static boolean getUserFirstInit(Context context) {
        preferences = context.getSharedPreferences(TAG_FIRST_INIT, 0);
        return preferences.getBoolean(TAG_FIRST_INIT, false);
    }

    public static void saveScreenMode(Context context, boolean is34mode) {
        preferences = context.getSharedPreferences(TAG_SCREEN_MODE, 0);
        preferences.edit()
                .putBoolean(TAG_SCREEN_MODE, is34mode)
                .commit();
    }

    public static boolean checkScreenIs34Mode(Context context) {
        preferences = context.getSharedPreferences(TAG_SCREEN_MODE, 0);
        return preferences.getBoolean(TAG_SCREEN_MODE, false);
    }

    /**
     * Save Language list
     *
     * @param context
     * @param data
     */
    public static void saveLanguageList(Context context, String data) {
        preferences = context.getSharedPreferences(TAG_LANGUAGE_LIST, 0);
        preferences.edit()
                .putString(TAG_LANGUAGE_LIST, data)
                .apply();
    }

    /**
     * Get Language list
     *
     * @param context
     * @return
     */
    public static String getLanguageList(Context context) {
        preferences = context.getSharedPreferences(TAG_LANGUAGE_LIST, 0);
        return preferences.getString(TAG_LANGUAGE_LIST, "");
    }

    /**
     * save my flights first
     *
     * @param context
     * @param first
     */
    public static void saveMyFlightsFirst(Context context, boolean first) {
        preferences = context.getSharedPreferences(TAG_MY_FLIGHTS_FIRST, 0);
        preferences.edit()
                .putBoolean(TAG_MY_FLIGHTS_FIRST, first)
                .apply();
    }

    /**
     * Get my flights first
     *
     * @param context
     * @return
     */
    public static boolean getMyFlightsFirst(Context context) {
        preferences = context.getSharedPreferences(TAG_MY_FLIGHTS_FIRST, 0);
        return preferences.getBoolean(TAG_MY_FLIGHTS_FIRST, true);
    }

    /**
     * Restore config
     *
     * @param context
     * @param deviceID
     */
    public static void saveDeviceID(Context context, String deviceID) {
        preferences = context.getSharedPreferences(TAG_RESTORE, 0);
        preferences.edit()
                .putString(TAG_RESTORE, deviceID)
                .apply();
    }

    /**
     * Restore config
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        preferences = context.getSharedPreferences(TAG_RESTORE, 0);
        return preferences.getString(TAG_RESTORE, "");
    }
}
