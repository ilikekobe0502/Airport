package com.whatmedia.ttia.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.main.MainActivity;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class FlightClockBroadcast extends BroadcastReceiver {
    private final static String TAG = FlightClockBroadcast.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        createNotification(context, "您的航班將要起飛");
    }

    private void createNotification(Context context, String message) {
        Intent i = new Intent(context, FlightClockIntentService.class);
        context.startService(i);
    }
}
