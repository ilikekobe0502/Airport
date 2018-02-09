package com.whatsmedia.ttia.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.page.main.MainActivity;

import java.util.Random;

/**
 * Created by neo_mac on 2017/8/21.
 */

public class FCMMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM", "onMessageReceived:" + remoteMessage.getFrom());

        // TODO: 2017/11/16 接Server來的Type資料
        String type = remoteMessage.getData().get(MainActivity.TAG_NOTIFICATION);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.TAG_NOTIFICATION, type);

        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLargeIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.icon)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true);
        } else {
            builder.setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true);
        }

        //Click intent
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(new Random().nextInt(900) + 65, notificationCompat);
    }
}
