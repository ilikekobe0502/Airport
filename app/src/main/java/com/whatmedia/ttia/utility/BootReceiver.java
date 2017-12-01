package com.whatmedia.ttia.utility;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.whatmedia.ttia.services.IBeacon;
import com.whatmedia.ttia.services.MyLocationService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent beacons = new Intent(context, IBeacon.class);
            context.startService(beacons);

            Intent MyLocationService = new Intent(context, MyLocationService.class);
            context.startService(MyLocationService);
        }
    }
}
