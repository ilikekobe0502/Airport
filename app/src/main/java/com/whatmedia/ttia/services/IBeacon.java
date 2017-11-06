package com.whatmedia.ttia.services;


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.splunk.mint.Mint;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.data.BeaconInfoData;

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

    private NewApiConnect mNewApiConnect;
    private BeaconManager mBeaconManager;
    private Region mRegion;
    private HashMap<String, Integer> mMap = new HashMap<>();
    private long date = System.currentTimeMillis() / day_millseconds;
    private int mTokenErrorCount = 0;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private boolean mSend = false;
    private BeaconInfoData mBeaconInfoData = new BeaconInfoData();


    //    private List<String> mAlreadySendMinorID = new ArrayList<>();
    private int mTempCount;

    public IBeacon() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBeaconManager = BeaconManager.getInstanceForApplication(this);
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");
        mBeaconManager.getBeaconParsers().add(beaconParser);
        //掃描頻率
        mBeaconManager.setForegroundBetweenScanPeriod(2000L);
        mBeaconManager.bind(this);
        mRegion = new Region("NeoIdentifier", null, null, null);
        mNewApiConnect = NewApiConnect.getInstance(this);


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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (System.currentTimeMillis() / day_millseconds != date) {
                    mMap.clear();
                    date = System.currentTimeMillis() / day_millseconds;
                }
                for (Beacon beacon : beacons) {
                    Log.e("IBeacon", beacon.toString() + ", RSSI:" + beacon.getRssi() + ", TxPower:" + beacon.getTxPower());
                    if (!mSend && (beacon.getId1().toString().toLowerCase().equals(BEACON_UUID_1) || beacon.getId1().toString().toLowerCase().equals(BEACON_UUID_2)) && beacon.getRssi() > -90) {

                        String minorID = beacon.getId3().toString();
                        if (!mMap.containsKey(minorID)) {
                            mMap.put(minorID, 0);
                            mSend = true;
                            mTokenErrorCount = 0;
                            uploadBeacon(minorID);
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

    public void uploadBeacon(final String minorID) {
        Log.e("IBeacon", "mTokenErrorCount:" + mTokenErrorCount + ", minorID:" + minorID + ", uploadBeacon(minorID) call.");


        mBeaconInfoData.setBeaconId(minorID);
        mNewApiConnect.uploadBeacon(mBeaconInfoData.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                Log.e(TAG, "registerUser failure");
                mSend = false;
                mMap.remove(minorID);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                //success
                Log.d("IBeacon", "registerUser() success, minorID:" + minorID);
                mSend = false;
            }
        });
    }
}
