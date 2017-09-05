package com.whatmedia.ttia.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.main.MainActivity;

import java.util.Random;

/**
 * Created by neo_mac on 2017/8/21.
 */

public class FCMMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM", "onMessageReceived:" + remoteMessage.getFrom());

        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLargeIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.icon)
                    .setAutoCancel(true);
        } else {
            builder.setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
        }

        //Click intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(new Random().nextInt(900) + 65, notificationCompat);
    }
}
