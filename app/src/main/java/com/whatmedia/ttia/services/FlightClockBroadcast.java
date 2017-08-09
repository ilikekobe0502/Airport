package com.whatmedia.ttia.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyContract;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class FlightClockBroadcast extends BroadcastReceiver {
    private final static String TAG = FlightClockBroadcast.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        createNotification(context, intent);
    }

    private void createNotification(Context context, Intent intent) {
        Intent i = new Intent(context, FlightClockIntentService.class);
        i.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_TIME_STRING, intent.getExtras().getString(MyFlightsNotifyContract.TAG_NOTIFY_TIME_STRING));
        i.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_ID, intent.getExtras().getInt(MyFlightsNotifyContract.TAG_NOTIFY_ID));
        context.startService(i);
    }
}
