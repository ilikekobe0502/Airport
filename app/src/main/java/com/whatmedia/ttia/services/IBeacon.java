package com.whatmedia.ttia.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

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

public class IBeacon extends Service implements BeaconConsumer {
    public static final String BEACON_UUID_1 = "A0000000-0000-0000-0000-000000000000";
    public static final String BEACON_UUID_2 = "B0000000-0000-0000-0000-000000000000";
    public static final String mBeacon = "e20a39f4-73f5-4bc4-a12f-17d1ad07a9a6";

    private ApiConnect mApiConnect;
    private BeaconManager mBeaconManager;
    private Region mRegion;
    private HashMap<String,Integer> mMap = new HashMap<>();
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
                for(Beacon beacon: beacons){
                    Log.e("IBeacon",beacon.toString() +", RSSI:"+beacon.getRssi()+", TxPower:"+beacon.getTxPower());
//                    Log.e("IBeacon","beacon.getId1().equals(mBeacon):"+beacon.getId1().equals(mBeacon));
//                    Log.e("IBeacon","beacon.getRssi() > -70:"+(beacon.getRssi() > -70));
                    //若該beacon的UUID == 公司所設定的那兩組UUID 且 RSSI > -70 才做以下動作
                    if((beacon.getId1().toString().equals(BEACON_UUID_1) || beacon.getId1().toString().equals(BEACON_UUID_2)) && beacon.getRssi() > -70){
                        String minorID = beacon.getId3().toString();
                        if(mMap.containsKey(minorID)){
                            Log.e("IBeacon","mMap.containsKey(minorID) true");
                            mTempCount = mMap.get(minorID);
                            mTempCount++;
                            mMap.put(minorID,mTempCount);
                            if(mTempCount == 10){
                                Log.e("IBeacon","saveAchievement(minorID) call.");
                                mMap.put(minorID,0);

                                //入境 若minorID == 1,2,34,35則進行新增使用者的動作
                                if(minorID.equals("1") || minorID.equals("2") || minorID.equals("34") || minorID.equals("35")){
                                    changeUserStatus(true);
                                    //出境 若minorID == 32,33則進行刪除使用者的動作
                                }else if(minorID.equals("32") || minorID.equals("33")){
                                    changeUserStatus(false);
                                }else{
                                    //其餘的minorID歸類為 新增使用者抵達Beacon的動作
                                    saveAchievement(minorID);
                                }
                            }else{
                                Log.e("IBeacon","saveAchievement(minorID) no call. mTempCount:"+mTempCount);
                            }
                        }else{
                            Log.e("IBeacon","mMap.containsKey(minorID) false");
                            mMap.put(minorID,1);
                        }
                    }else{
                        Log.e("IBeacon","!beacon.getId1().equals(beacon) && beacon.getRssi() <= -70");
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

    public void saveAchievement(final String minorID){
        mApiConnect.saveAchievement(minorID,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    //success
                    Log.e("IBeacon","saveAchievement() success, minorID:"+minorID);
                } else {
                    //fail
                }
            }
        });
    }

    public void changeUserStatus(final boolean isAdd){
        mApiConnect.registerUser(isAdd,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    //success
                    Log.e("IBeacon","saveAchievement() success, isAdd:"+isAdd);
                } else {
                    //fail
                }
            }
        });
    }
}
