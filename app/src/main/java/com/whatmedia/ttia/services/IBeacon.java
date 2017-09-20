package com.whatmedia.ttia.services;


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.splunk.mint.Mint;
import com.whatmedia.ttia.connect.ApiConnect;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*


                                 |~~~~~~~|
                                 |       |
                                 |       |
                                 |       |
                                 |       |
                                 |       |
      |~.\\\_\~~~~~~~~~~~~~~xx~~~         ~~~~~~~~~~~~~~~~~~~~~/_//;~|
      |  \  o \_         ,XXXXX),                         _..-~ o /  |
      |    ~~\  ~-.     XXXXX`)))),                 _.--~~   .-~~~   |
       ~~~~~~~`\   ~\~~~XXX' _/ ';))     |~~~~~~..-~     _.-~ ~~~~~~~
                `\   ~~--`_\~\, ;;;\)__.---.~~~      _.-~
                  ~-.       `:;;/;; \          _..-~~
                     ~-._      `''        /-~-~
                         `\              /  /
                           |         ,   | |
                            |  '        /  |
                             \/;          |
                              ;;          |
                              `;   .       |
                              |~~~-----.....|
                             | \             \
                            | /\~~--...__    |
                            (|  `\       __-\|
                            ||    \_   /~    |
                            |)     \~-'      |
                             |      | \      '
                             |      |  \    :
                              \     |  |    |
                               |    )  (    )
                                \  /;  /\  |
                                |    |/   |
                                |    |   |
                                 \  .'  ||
                                 |  |  | |
                                 (  | |  |
                                 |   \ \ |
                                 || o `.)|
                                 |`\\\\) |
                                 |       |
                                 |       |
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                      耶穌保佑                永無 BUG
*/
public class IBeacon extends Service implements BeaconConsumer {
    private static final String TAG = IBeacon.class.getSimpleName();
    private final static String TAG_DEVICE_NAME = android.os.Build.MODEL;

    public static final String BEACON_UUID_1 = "a0000000-0000-0000-0000-000000000000";
    public static final String BEACON_UUID_2 = "b0000000-0000-0000-0000-000000000000";
    public static final String BEACON_UUID_3 = "e8229ba5-5ee0-4fb5-9648-366a7f97a70a";
    private final long day_millseconds = 86400000;
    private static final Object mBeaconLocker = new Object();

    private ApiConnect mApiConnect;
    private BeaconManager mBeaconManager;
    private Region mRegion;
    private HashMap<String, Integer> mMap = new HashMap<>();
    private long date = System.currentTimeMillis() / day_millseconds;
    private int mTokenErrorCount = 0;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private boolean mSend = false;


    //    private List<String> mAlreadySendMinorID = new ArrayList<>();
    private int mTempCount;

    public IBeacon() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        //adam設的 不知道用意
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");
        mBeaconManager.getBeaconParsers().add(beaconParser);
        //掃描頻率
        mBeaconManager.setForegroundBetweenScanPeriod(2000L);
        mBeaconManager.bind(this);
        //好像可以做filter的功能
        mRegion = new Region("NeoIdentifier", null, null, null);
        mApiConnect = ApiConnect.getInstance(this);


        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled())//判斷目前bluetooth狀態 開啟會回傳true
            Mint.logEvent(TAG_DEVICE_NAME + "BlueOn");
        else
            Mint.logEvent(TAG_DEVICE_NAME + "BlueOff");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeaconManager.unbind(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                Log.e("date","date:"+date+", System.currentTimeMillis()/day_millseconds:"+System.currentTimeMillis()/day_millseconds+", System.currentTimeMillis():"+System.currentTimeMillis());
                if (System.currentTimeMillis() / day_millseconds != date) {
                    mMap.clear();
                    date = System.currentTimeMillis() / day_millseconds;
                }
                for (Beacon beacon : beacons) {
                    Log.e("IBeacon", beacon.toString() + ", RSSI:" + beacon.getRssi() + ", TxPower:" + beacon.getTxPower());
                    if (!mSend && (beacon.getId1().toString().equals(BEACON_UUID_1) || beacon.getId1().toString().equals(BEACON_UUID_2)) && beacon.getRssi() > -90) {
                        String minorID = beacon.getId3().toString();
                        if (!mMap.containsKey(minorID)) {

//                            synchronized (mBeaconLocker) {
                            mSend = true;
                            mTokenErrorCount = 0;
                            changeUserStatus(minorID);
//                            }
                        }
                    }
                }
            }
        });
        try {
            mBeaconManager.startRangingBeaconsInRegion(mRegion);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void changeUserStatus(final String minorID) {
        Log.e("IBeacon", "mTokenErrorCount:" + mTokenErrorCount + ", minorID:" + minorID + ", changeUserStatus(minorID) call.");
//        if (!mApiConnect.registerUser(minorID, new Callback() {
        mApiConnect.registerUser(minorID, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "registerUser failure");
                mSend = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    //success
                    Log.e("IBeacon", "registerUser() success, minorID:" + minorID);
                    mMap.put(minorID, 0);
                    mSend = false;
                } else {
                    Log.e(TAG, "registerUser failure");
                }
            }
        });
//        ) {
//            Log.e(TAG, "Token error : mTokenErrorCount = " + mTokenErrorCount);
//            mTokenErrorCount++;
//            if (mTokenErrorCount < 10) {
//                mApiConnect = null;
//                mApiConnect = ApiConnect.getInstance(getApplicationContext());
//                changeUserStatus(minorID);
//            } else {
//                mTokenErrorCount = 0;
//                 TODO: 2017/9/2 提醒重開APP
//            }
//        }
    }
}
