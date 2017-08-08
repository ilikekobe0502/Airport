package com.whatmedia.ttia.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyContract;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class FlightClockIntentService extends IntentService {
    private final static String TAG = FlightClockIntentService.class.getSimpleName();
    private final static int ID = 123;

    public FlightClockIntentService() {
        super(FlightClockIntentService.class.getSimpleName());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FlightClockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(getString(R.string.flights_info_flights_notify))
                .setContentText(getString(R.string.my_flights_notify_message, intent.getExtras().get(MyFlightsNotifyContract.TAG_NOTIFY_KEY)))
                .setSmallIcon(R.drawable.home_02);

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(ID, notificationCompat);
    }
}
