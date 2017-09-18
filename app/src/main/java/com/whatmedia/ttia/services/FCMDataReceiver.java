package com.whatmedia.ttia.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.whatmedia.ttia.utility.Util;

/**
 * Created by neo_mac on 2017/9/18.
 */

public class FCMDataReceiver extends WakefulBroadcastReceiver {
    private final static String TAG = "FCMDataReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        Util.wakeUpScreen(context, 6000, TAG);
    }
}
