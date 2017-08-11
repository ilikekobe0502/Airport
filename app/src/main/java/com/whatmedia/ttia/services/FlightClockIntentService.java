package com.whatmedia.ttia.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyContract;
import com.whatmedia.ttia.response.data.ClockData;
import com.whatmedia.ttia.response.data.ClockDataList;
import com.whatmedia.ttia.utility.Preferences;

import java.util.List;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class FlightClockIntentService extends IntentService {
    private final static String TAG = FlightClockIntentService.class.getSimpleName();
    private Gson mGson;

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
        if (intent.getExtras() != null) {
            int id = intent.getExtras().getInt(MyFlightsNotifyContract.TAG_NOTIFY_ID);
            String timeString = !TextUtils.isEmpty(intent.getExtras().getString(MyFlightsNotifyContract.TAG_NOTIFY_TIME_STRING)) ? intent.getExtras().getString(MyFlightsNotifyContract.TAG_NOTIFY_TIME_STRING) : "";

            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle(getString(R.string.flights_info_flights_notify))
                    .setContentText(getString(R.string.my_flights_notify_message, timeString))
                    .setSmallIcon(R.drawable.home_02);

            Notification notificationCompat = builder.build();
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(id, notificationCompat);

//            deleteReceiveNotification(id);
        } else {
            Log.e(TAG, "Service bundle is error");
        }
    }

    /**
     * 收到推播之後把推播刪除
     */
    private void deleteReceiveNotification(int receiveId) {
        List<ClockData> datas = ClockDataList.newInstance(Preferences.getClockData(getApplicationContext()));
        for (ClockData item : datas) {
            if (item.getId() == receiveId) {
                datas.remove(item);
                break;
            }
        }
        if (mGson == null)
            mGson = new Gson();
        String json = mGson.toJson(datas);
        Preferences.saveClockData(getApplicationContext(), json);
    }
}
